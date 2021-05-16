package com.example.vocabapp.Fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;
import com.example.vocabapp.API.ApiClient;
import com.example.vocabapp.API.DictionaryEntriesApi;
import com.example.vocabapp.Activities.SearchResultActivity;
import com.example.vocabapp.Data.DataHelper;
import com.example.vocabapp.Data.WordSuggestion;
import com.example.vocabapp.OxfordDictionary.Definition;
import com.example.vocabapp.OxfordDictionary.DefinitionRenderer;
import com.example.vocabapp.R;
import com.example.vocabapp.model.Entry;
import com.example.vocabapp.model.LexicalCategory;
import com.pedrogomez.renderers.ListAdapteeCollection;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FragmentSlidingSearch extends BaseFragment {
    private final String TAG = "BlankFragment";
    public final String WORD_SELECTION = "wordselection";

    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    private static final long ANIM_DURATION = 350;

    private View mHeaderView;
    private View mDimSearchViewBackground;
    private ColorDrawable mDimDrawable;
    private FloatingSearchView mSearchView;

    private boolean mIsDarkSearchTheme = false;

    private String mLastQuery = "";

    FragmentSearchListener mListener;

    private AnimatorSet mSetRightAnimator;
    private AnimatorSet mSetLeftAnimator;
    private boolean mIsBackVisible = false;
    private View mCardFrontLayout;
    private View mCardBackLayout;
    private ApiClient apiClient;
    private RecyclerView recyclerView;
    private DictionaryEntriesApi entriesApi;


    public FragmentSlidingSearch() {
        // Required empty public constructor
    }

    public interface FragmentSearchListener {
        void onSuggestionOrSearchClick(String string);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FragmentSearchListener) context;
        } catch (ClassCastException e) {
            return;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sliding_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSearchView = (FloatingSearchView) view.findViewById(R.id.floating_search_view);
        mHeaderView = view.findViewById(R.id.header_view);

        mDimSearchViewBackground = view.findViewById(R.id.dim_background);
        mDimDrawable = new ColorDrawable(Color.BLACK);
        mDimDrawable.setAlpha(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDimSearchViewBackground.setBackground(mDimDrawable);
        } else {
            mDimSearchViewBackground.setBackgroundDrawable(mDimDrawable);
        }

        apiClient = new ApiClient();

        entriesApi = apiClient.get(DictionaryEntriesApi.class);
        recyclerView = view.findViewById(R.id.list_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //recyclerView = view.findViewById(R.id.recycler_view);
//        itemSuggest = DataHelper.getHistory(this.getContext(),3);
//        adapter = new Adapter(this.getContext(),itemSuggest);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(linearLayoutManager);

        setupFloatingSearch();
        setupDrawer();

//        mCardBackLayout = view.findViewById(R.id.card_back);
//        mCardFrontLayout = view.findViewById(R.id.card_front);
//        loadAnimations();
//        changeCameraDistance();
//        view.setOnClickListener(this::flipCard);
    }


    private void performSearch(final String searchText) {
        entriesApi.getDictionaryEntries("en-gb",searchText,"application/json","405175e5","037c088b3280b6904c4d7733d4b95ab8")
                .doOnSubscribe(d->hideKeyboard())
                .flatMap(re-> Observable.fromIterable(re.getResults()))
                .flatMap(he -> Observable.fromIterable(he.getLexicalEntries()))
                .flatMap(le -> Observable.fromIterable(le.getEntries()).map(e -> new FragmentSlidingSearch.CategorizedEntry(searchText, le.getLexicalCategory(), e)))
                .flatMap(ce -> Observable.fromIterable(ce.entry.getSenses()).map(s -> {
                    if(s.getExamples()!=null)
                        return new Definition(ce.category.getText(), ce.word, ce.entry, s, s.getExamples().get(0).getText());
                else return new Definition(ce.category.getText(), ce.word, ce.entry, s, "don't have example");}))
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



//    private void loadAnimations() {
//        mSetRightAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this.getContext(), R.animator.animation);
//        mSetLeftAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this.getContext(), R.animator.animation2);
//    }

    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        mCardBackLayout.setCameraDistance(scale);
        mCardFrontLayout.setCameraDistance(scale);
    }


    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetRightAnimator.setTarget(mCardFrontLayout);
            mSetLeftAnimator.setTarget(mCardBackLayout);
            mSetRightAnimator.start();
            mSetLeftAnimator.start();
            mIsBackVisible = true;
        } else {
            mSetRightAnimator.setTarget(mCardBackLayout);
            mSetLeftAnimator.setTarget(mCardFrontLayout);
            mSetRightAnimator.start();
            mSetLeftAnimator.start();
            mIsBackVisible = false;
        }
    }


    private void setupFloatingSearch() {
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();

                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    DataHelper.findSuggestions(getActivity(), newQuery, 5,
                            FIND_SUGGESTION_SIMULATED_DELAY, new DataHelper.OnFindSuggestionsListener() {

                                @Override
                                public void onResults(List<WordSuggestion> results) {

                                    //this will swap the data and
                                    //render the collapse/expand animations as necessary
                                    mSearchView.swapSuggestions(results);
                                    //let the users know that the background
                                    //process has completed
                                    mSearchView.hideProgress();
                                }
                            });
                }

                Log.d(TAG, "onSearchTextChanged()");
            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                mLastQuery = searchSuggestion.getBody();
                performSearch(mLastQuery);
               // mListener.onSuggestionOrSearchClick(mLastQuery);
            }

            @Override
            public void onSearchAction(String query) {
                mLastQuery = query;
                //mListener.onSuggestionOrSearchClick(mLastQuery);
                performSearch(mLastQuery);
                Log.d(TAG, "onSearchAction()");
            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                int headerHeight = 195;
                ObjectAnimator anim = ObjectAnimator.ofFloat(mSearchView, "translationY",
                        headerHeight, 0);
                anim.setDuration(350);
                fadeDimBackground(0, 150, null);
                anim.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //show suggestions when search bar gains focus (typically history suggestions)
                        mSearchView.swapSuggestions(DataHelper.getHistory(getActivity(), 3));

                    }
                });
                anim.start();

                Log.d(TAG, "onFocus()");
            }

            @Override
            public void onFocusCleared() {
                int headerHeight = 195;
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


        //handle menu clicks the same way as you would
        //in a regular activity
//        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
//            @Override
//            public void onActionMenuItemSelected(MenuItem item) {
//
//                if (item.getItemId() == R.id.action_change_colors) {
//
//                    mIsDarkSearchTheme = true;
//
//                    //demonstrate setting colors for items
//                    mSearchView.setBackgroundColor(Color.parseColor("#787878"));
//                    mSearchView.setViewTextColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setHintTextColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setActionMenuOverflowColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setMenuItemIconColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setLeftActionIconColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setClearBtnColor(Color.parseColor("#e9e9e9"));
//                    mSearchView.setDividerColor(Color.parseColor("#BEBEBE"));
//                    mSearchView.setLeftActionIconColor(Color.parseColor("#e9e9e9"));
//                } else {
//
//                    //just print action
//                    Toast.makeText(getActivity().getApplicationContext(), item.getTitle(),
//                            Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        //use this listener to listen to menu clicks when app:floatingSearch_leftAction="showHome"
        mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
            @Override
            public void onHomeClicked() {

                Log.d(TAG, "onHomeClicked()");
            }
        });

        /*
         * Here you have access to the left icon and the text of a given suggestion
         * item after as it is bound to the suggestion list. You can utilize this
         * callback to change some properties of the left icon and the text. For example, you
         * can load the left icon images using your favorite image loading library, or change text color.
         *
         *
         * Important:
         * Keep in mind that the suggestion list is a RecyclerView, so views are reused for different
         * items in the list.
         */
        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {
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
            }

        });

        /*
         * When the user types some text into the search field, a clear button (and 'x' to the
         * right) of the search text is shown.
         *
         * This listener provides a callback for when this button is clicked.
         */
        mSearchView.setOnClearSearchActionListener(new FloatingSearchView.OnClearSearchActionListener() {
            @Override
            public void onClearSearchClicked() {

                Log.d(TAG, "onClearSearchClicked()");
            }
        });
    }


    @Override
    public boolean onActivityBackPress() {
        //if mSearchView.setSearchFocused(false) causes the focused search
        //to close, then we don't want to close the activity. if mSearchView.setSearchFocused(false)
        //returns false, we know that the search was already closed so the call didn't change the focus
        //state and it makes sense to call supper onBackPressed() and close the activity
        if (!mSearchView.setSearchFocused(false)) {
            return false;
        }
        return true;
    }

    private void setupDrawer() {
        attachSearchViewActivityDrawer(mSearchView);
    }

    private void fadeDimBackground(int from, int to, Animator.AnimatorListener listener) {
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int value = (Integer) animation.getAnimatedValue();
                mDimDrawable.setAlpha(value);
            }
        });
        if (listener != null) {
            anim.addListener(listener);
        }
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }
}

