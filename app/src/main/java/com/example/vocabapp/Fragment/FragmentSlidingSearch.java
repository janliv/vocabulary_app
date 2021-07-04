package com.example.vocabapp.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.example.vocabapp.Data.DataHelper;
import com.example.vocabapp.Data.DataSource.DataRepository;
import com.example.vocabapp.Data.DataSource.DiskDataSource;
import com.example.vocabapp.Data.DataSource.MemoryDataSource;
import com.example.vocabapp.Data.DataSource.NetworkDataSource;
import com.example.vocabapp.Data.WordSuggestion;
import com.example.vocabapp.DatabaseHelper.DatabaseAccess;
import com.example.vocabapp.InternetConnection.Connection;
import com.example.vocabapp.OxfordDictionary.Definition;
import com.example.vocabapp.OxfordDictionary.DefinitionRenderer;
import com.example.vocabapp.R;
import com.example.vocabapp.Users.UserDataHelper;
import com.example.vocabapp.model.Entry;
import com.example.vocabapp.model.LexicalCategory;
import com.pedrogomez.renderers.ListAdapteeCollection;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FragmentSlidingSearch extends BaseFragment {
    private final String TAG = "BlankFragment";
    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    private static final long ANIM_DURATION = 350;

    private View mHeaderView;
    private View mDimSearchViewBackground;
    private ColorDrawable mDimDrawable;
    private FloatingSearchView mSearchView;

    private boolean mIsDarkSearchTheme = false;

    private String mLastQuery = "";
    private RecyclerView recyclerView;

    private MemoryDataSource memoryDataSource;
    private DiskDataSource diskDataSource;
    private NetworkDataSource networkDataSource;
    private static final String SHAREDPREF = "SHAREDPREF";
    private List<WordSuggestion> l;
    private int i = 0;

    public FragmentSlidingSearch() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliding_search, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("LASTWORD", Context.MODE_PRIVATE);
        String lastword = sharedPreferences.getString("lastword", "welcome");
        mSearchView.setSearchBarTitle(lastword);
        performSearch(lastword);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (l == null) return;
        if (l.size() > 0) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LASTWORD", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("lastword", l.get(l.size() - 1).getBody()).apply();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = view.findViewById(R.id.floating_search_view);
        mHeaderView = view.findViewById(R.id.header_view);

        mDimSearchViewBackground = view.findViewById(R.id.dim_background);
        mDimDrawable = new ColorDrawable(Color.BLACK);
        mDimDrawable.setAlpha(0);
        mDimSearchViewBackground.setBackground(mDimDrawable);

        recyclerView = view.findViewById(R.id.list_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        setupFloatingSearch();
        setupDrawer();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHAREDPREF, Context.MODE_PRIVATE);
        memoryDataSource = new MemoryDataSource();
        diskDataSource = new DiskDataSource(sharedPreferences);
        networkDataSource = new NetworkDataSource(getContext());
        DataHelper.setsColorSuggestions(getContext());
    }

    @SuppressLint("CheckResult")
    private void performSearch(final String searchText) {
        DataRepository dataRepository = new DataRepository(memoryDataSource, diskDataSource, networkDataSource);
        addHistoryWord(searchText);
        //entriesApi.getDictionaryEntries("en-us", searchText, "application/json", "0b61a8b1", "aae13662958f2a69eb82099315c1375d")
        dataRepository.getData(searchText)
                .doOnSubscribe(d -> hideKeyboard())
                .flatMap(re -> Observable.fromIterable(re.getResults()))
                .flatMap(he -> Observable.fromIterable(he.getLexicalEntries()))
                .flatMap(le -> Observable.fromIterable(le.getEntries()).map(e -> new CategorizedEntry(searchText, le.getLexicalCategory(), e)))
                .flatMap(ce -> Observable.fromIterable(ce.entry.getSenses()).map(s -> new Definition(ce.category.getText(), ce.word, ce.entry, s)))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::createAdapter)
                .subscribe(this::updateRecyclerView);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(recyclerView.getWindowToken(), 0);
    }

    @NonNull
    private RVRendererAdapter<Definition> createAdapter(List<Definition> definitions) {
        RendererBuilder<Definition> builder = new RendererBuilder<Definition>()
                .bind(Definition.class, new DefinitionRenderer());
        ListAdapteeCollection<Definition> collection = new ListAdapteeCollection<>(definitions);
        return new RVRendererAdapter<>(builder, collection);
    }

    private void updateRecyclerView(RVRendererAdapter<Definition> adapter) {
        if (recyclerView.getAdapter() != null) {
            recyclerView.swapAdapter(adapter, true);
        } else {
            recyclerView.setAdapter(adapter);
        }
        recyclerView.scheduleLayoutAnimation();
    }

    private static class CategorizedEntry {
        final String word;
        final LexicalCategory category;
        final Entry entry;

        CategorizedEntry(String word, LexicalCategory category, Entry entry) {
            this.word = word;
            this.category = category;
            this.entry = entry;
        }
    }


    private void setupFloatingSearch() {
        mSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> {

            if (!oldQuery.equals("") && newQuery.equals("")) {
                mSearchView.clearSuggestions();

            } else {
                mSearchView.showProgress();

                DataHelper.findSuggestions(getActivity(), newQuery, 5,
                        FIND_SUGGESTION_SIMULATED_DELAY, results -> {

                            mSearchView.swapSuggestions(results);
                            if (results.size() > 0)
                                mLastQuery = results.get(0).getBody();
                            mSearchView.hideProgress();
                        });
            }

            Log.d(TAG, "onSearchTextChanged()");
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                mLastQuery = searchSuggestion.getBody();
                performSearch(mLastQuery);
                mSearchView.setSearchFocused(false);
            }

            @Override
            public void onSearchAction(String query) {
                //mLastQuery = query;
                if (!mLastQuery.equals(""))
                    performSearch(mLastQuery);
                Log.d(TAG, "onSearchAction()");
                mSearchView.setSearchFocused(false);
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                int headerHeight = 205;
                ObjectAnimator anim = ObjectAnimator.ofFloat(mSearchView, "translationY",
                        headerHeight, 0);
                anim.setDuration(350);
                fadeDimBackground(0, 150, null);
                anim.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        new UserDataHelper().readWordSearched(new UserDataHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<String> list, String key) {
                                l = new ArrayList<>();
                                for (int i = list.size() - 1; i >= 0; i--) {
                                    l.add(new WordSuggestion(list.get(i), true));
                                }
                                if (mSearchView.isSearchBarFocused())
                                    mSearchView.swapSuggestions(l);
                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });

                        //show suggestions when search bar gains focus (typically history suggestions)

                    }
                });
                anim.start();

                Log.d(TAG, "onFocus()");
            }

            @Override
            public void onFocusCleared() {
                int headerHeight = 205;
                ObjectAnimator anim = ObjectAnimator.ofFloat(mSearchView, "translationY",
                        0, headerHeight);
                anim.setDuration(350);
                anim.start();
                fadeDimBackground(150, 0, null);

                //set the title of the bar so that when focus is returned a new query begins
                mSearchView.setSearchBarTitle(mLastQuery);

                //you can also set setSearchText(...) to make keep the query there when not focused and when focus returns
                //mSearchView.setSearchText(searchSuggestion.getBody());

                Log.d(TAG, "onFocusCleared()");
            }
        });
        mSearchView.setOnHomeActionClickListener(() -> Log.d(TAG, "onHomeClicked()"));


        mSearchView.setOnBindSuggestionCallback((suggestionView, leftIcon, textView, item, itemPosition) -> {
            WordSuggestion colorSuggestion = (WordSuggestion) item;

            String textColor = mIsDarkSearchTheme ? "#ffffff" : "#000000";
            String textLight = mIsDarkSearchTheme ? "#bfbfbf" : "#787878";

            if (colorSuggestion.getIsHistory()) {
                leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                        R.drawable.ic_history_black_24dp, null));

                Util.setIconColor(leftIcon, Color.parseColor(textColor));
                leftIcon.setAlpha(.36f);
            } else {
                leftIcon.setAlpha(0.0f);
                leftIcon.setImageDrawable(null);
            }

            textView.setTextColor(Color.parseColor(textColor));
            String text = colorSuggestion.getBody()
                    .replaceFirst(mSearchView.getQuery(),
                            "<font color=\"" + textLight + "\">" + mSearchView.getQuery() + "</font>");
            textView.setText(Html.fromHtml(text));
        });

        mSearchView.setOnClearSearchActionListener(() -> Log.d(TAG, "onClearSearchClicked()"));
    }


    @Override
    public boolean onActivityBackPress() {
        return mSearchView.setSearchFocused(false);
    }

    private void setupDrawer() {
        attachSearchViewActivityDrawer(mSearchView);
    }

    private void fadeDimBackground(int from, int to, Animator.AnimatorListener listener) {
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        anim.addUpdateListener(animation -> {

            int value = (Integer) animation.getAnimatedValue();
            mDimDrawable.setAlpha(value);
        });
        if (listener != null) {
            anim.addListener(null);
        }
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

    private void addHistoryWord(String word) {
        if (l == null || word.equals("welcome")) return;
        for (WordSuggestion wordSuggestion : l)
            if (wordSuggestion.getBody().equals(word))
                return;
        new UserDataHelper().addNewWordSearched(word, new UserDataHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<String> list, String key) {

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {
                Log.d("TAG", "added word search");
            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    public String randomString() {
        List<WordSuggestion> list;
        list = DatabaseAccess.getInstance(getContext()).getSuggestions();
        int index = (int) (Math.random() * list.size());
        return list.get(index).getBody();
    }
}

