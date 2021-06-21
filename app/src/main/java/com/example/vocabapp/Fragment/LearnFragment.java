package com.example.vocabapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vocabapp.LearnModels.Word;
import com.example.vocabapp.LearnService.WordService;
import com.example.vocabapp.R;
import com.example.vocabapp.Users.UserDataHelper;
import com.example.vocabapp.WordsReview;
import com.hanks.htextview.fade.FadeTextView;
import com.hanks.htextview.fall.FallTextView;
import com.hanks.htextview.line.LineText;
import com.hanks.htextview.line.LineTextView;
import com.hanks.htextview.scale.ScaleTextView;
import com.hanks.htextview.typer.TyperTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LearnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LearnFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Animation flyuptodown, flydowntoupvoice, flydowntouplearn, flydowntoupnext;
    private TextView word;
    private TextView definition;
    private TextView wordtype;
    private TextView phonetic;
    private ImageView wordHolder, line, voice;
    private ImageButton btn1;
    private ImageButton btn2;
    private ImageButton btn3;
    private ImageButton btn4;
    private String defOfWord = "";
    private String typeOfWord = "";
    private String phoneticOfWord = "";
    private int indexList = 0;
    private List<String> wordsListReview = new ArrayList<String>();
    private List<String> defListReview = new ArrayList<String>();
    private String voiceURL = "";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LearnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LearnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LearnFragment newInstance(String param1, String param2) {
        LearnFragment fragment = new LearnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_learn, container, false);
        flyuptodown = AnimationUtils.loadAnimation(getContext(), R.anim.fly_up_to_down);
        flydowntoupvoice = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_voice);
        flydowntouplearn = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_learn);
        flydowntoupnext = AnimationUtils.loadAnimation(getContext(), R.anim.fly_down_to_up_next);

        wordHolder = view.findViewById(R.id.word_holder);
        line = view.findViewById(R.id.line);
        word = view.findViewById(R.id.word);
        voice = view.findViewById(R.id.voice);
        definition = view.findViewById(R.id.definition);
        definition.setMovementMethod(new ScrollingMovementMethod());
        wordtype = view.findViewById(R.id.wordtype);
        phonetic = view.findViewById(R.id.phonetic);
        btn1 = view.findViewById(R.id.next_word);
        btn2 = view.findViewById(R.id.learn_this_word);
        btn3 = view.findViewById(R.id.voice);
        btn4 = view.findViewById(R.id.review_words);

        btn4.setVisibility(View.INVISIBLE);

        // Animations
        btn1.setVisibility(View.INVISIBLE);
        btn2.setVisibility(View.INVISIBLE);

        String w = randomWord();
        word.setText(w);

        btn1.setOnClickListener(v -> {
            String wo = randomWord();
            word.setText(wo);
            phonetic.setText("");
            wordtype.setText("");
            definition.setText("");
            voiceURL = "";
        });

        btn2.setOnClickListener(v -> {
            getWordDefinition();
            addNewWordToFireBase(word.getText().toString());
        });

        btn3.setOnClickListener(v -> playVoice());

        btn4.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), WordsReview.class);
            intent.putStringArrayListExtra("wordsKey", (ArrayList<String>) wordsListReview);
            intent.putStringArrayListExtra("meaningKey", (ArrayList<String>) defListReview);
            startActivity(intent);

            wordsListReview.clear();
            defListReview.clear();
            indexList = 0;
            btn1.callOnClick();
            btn1.setVisibility(View.VISIBLE);
            btn4.setVisibility(View.GONE);
        });

        gone();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        delayAnim(wordHolder,flyuptodown,200);
        delayAnim(word,flyuptodown,100);
        delayAnim(line,flyuptodown,100);
        delayAnim(voice,flydowntoupvoice,100);
        delayAnim(btn2, flydowntouplearn, 400);
        delayAnim(btn1, flydowntoupnext, 600);
    }

    @Override
    public void onPause() {
        super.onPause();
        gone();
    }

    @Override
    public void onStop() {
        super.onStop();
        gone();
    }

    private void gone(){
        wordHolder.setVisibility(View.GONE);
        word.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        voice.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE);
        btn1.setVisibility(View.GONE);
    }

    private String randomWord() {
        int upperBound = 4244;
        Random r = new Random();
        int someRandomNo = r.nextInt(upperBound);
        String displayWord = getStreamTextByLine("words_list.txt", someRandomNo);
        return displayWord;
    }

    // Read a line from file
    @SuppressLint("LongLogTag")
    public String getStreamTextByLine(String fileName, int randomNumber) {
        String strOut = "";
        String line = "";
        int counter = 1;
        AssetManager assetManager = getContext().getAssets();
        try {
            InputStream in = assetManager.open(fileName);
            if (in != null) {
                InputStreamReader input = new InputStreamReader(in);
                BufferedReader buffreader = new BufferedReader(input);
                while ((line = buffreader.readLine()) != null) {
                    if (counter == randomNumber) {
                        strOut = line;
                    }
                    counter++;
                }
                in.close();
            } else {
                Log.e("Input Stream Problem",
                        "Input stream of text file is null");
            }
        } catch (Exception e) {
            Log.e("0003:Error in get stream", e.getMessage());
        }
        return strOut;
    }

    // Get definition of word
    private void getWordDefinition() {
        WordService.wordService.getWord(word.getText().toString()).enqueue(new Callback<List<Word>>() {
            @Override
            public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                List<Word> word = response.body();
                if (word != null) {
                    if (word.get(0).phonetics.isEmpty() || word.get(0).meanings.isEmpty()) {
                        Toast.makeText(getContext(), "Error occured, please skip this word!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        phoneticOfWord = word.get(0).phonetics.get(0).text;
                        phonetic.setText(phoneticOfWord);

                        voiceURL = word.get(0).phonetics.get(0).audio;

                        typeOfWord = word.get(0).meanings.get(0).partOfSpeech;
                        if (typeOfWord.contains("verb")) {
                            typeOfWord = "verb";
                        }
                        else if (typeOfWord.contains("determiner")) {
                            typeOfWord = "determiner";
                        }
                        wordtype.setText("(" + typeOfWord + ")");

                        defOfWord = word.get(0).meanings.get(0).definitions.get(0).definition;
                        definition.setText(defOfWord);
                        addWordToList(defOfWord);
                    }
                }
                else {
                    Toast.makeText(getContext(), "Error occured, please skip this word!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Word>> call, Throwable t) {
                Toast.makeText(getContext(), "Error occured, please skip this word!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addWordToList(String definition){
        if (indexList <= 4) {
            wordsListReview.add(indexList, word.getText().toString());
            if (indexList == 0 || !wordsListReview.get(indexList).equals(wordsListReview.get(indexList - 1))) {
                defListReview.add(indexList, definition);
                indexList++;
            }
            else {
                wordsListReview.remove(indexList);
                Toast.makeText(getContext(), "You've already learned this word!", Toast.LENGTH_SHORT).show();
            }
        }
        if (indexList > 4) {
            Toast.makeText(getContext(), "You've reached 5/5 words!", Toast.LENGTH_SHORT).show();
            btn1.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.VISIBLE);
        }
    }


    private void playVoice() {
        if (voiceURL.equals("")) {
            Toast.makeText(getContext(), "Learn this word first!", Toast.LENGTH_SHORT).show();
        }
        else {
            MediaPlayer mp = new MediaPlayer();
            try {
                mp.setDataSource(voiceURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                mp.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.start();
        }
    }

    private void delayAnim(ImageButton btn, Animation anim, long time) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.startAnimation(anim);
                btn.setVisibility(View.VISIBLE);
            }
        }, time);
    }

    private void addNewWordToFireBase(String word) {
        new UserDataHelper().addNewWordSeen(word, new UserDataHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<String> list, String key) {

            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {
                Log.d("TAG", "Added new Word Seen");
            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }

    @Override
    public boolean onActivityBackPress() {
        return false;
    }

    private void delayAnim(TextView btn, Animation anim, long time) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            btn.startAnimation(anim);
            btn.setVisibility(View.VISIBLE);
        }, time);
    }

    private void delayAnim(ImageView btn, Animation anim, long time) {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            btn.startAnimation(anim);
            btn.setVisibility(View.VISIBLE);
        }, time);
    }
}