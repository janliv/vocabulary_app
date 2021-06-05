package com.example.vocabapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vocabapp.LearnModels.Word;
import com.example.vocabapp.LearnService.WordService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Learn extends AppCompatActivity {

    private TextView word;
    private TextView definition;
    private TextView wordtype;
    private TextView phonetic;
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
    private String voiceURL;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        // *Bottom navigation bar*
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Learn selected
        bottomNavigationView.setSelectedItemId(R.id.learnId);

        // Item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homeId:
                        startActivity(new Intent(getApplicationContext(), Home.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.learnId:
                        return true;
                    case R.id.searchId:
                        startActivity(new Intent(getApplicationContext(), Search.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.achievementsId:
                        startActivity(new Intent(getApplicationContext(), Records.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        word = findViewById(R.id.word);
        definition = findViewById(R.id.definition);
        wordtype = findViewById(R.id.wordtype);
        phonetic = findViewById(R.id.phonetic);
        btn1 = findViewById(R.id.next_word);
        btn2 = findViewById(R.id.learn_this_word);
        btn3 = findViewById(R.id.voice);
        btn4 = findViewById(R.id.review_words);

        btn4.setVisibility(View.INVISIBLE);

        word.setText(randomWord());
        getVoice();
        getWordDefinition();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.setText(randomWord());
                phonetic.setText("");
                wordtype.setText("");
                definition.setText("");
                getVoice();
                getWordDefinition();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setWordDefinition();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVoice();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Learn.this, WordsReview.class);
                intent.putStringArrayListExtra("wordsKey", (ArrayList<String>) wordsListReview);
                intent.putStringArrayListExtra("meaningKey", (ArrayList<String>) defListReview);
                startActivity(intent);
            }
        });

    }

    // Generate random word
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
        AssetManager assetManager = getAssets();
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
                    phoneticOfWord = word.get(0).phonetics.get(0).text;
                    typeOfWord = word.get(0).meanings.get(0).partOfSpeech;
                    defOfWord = word.get(0).meanings.get(0).definitions.get(0).definition;
                }
            }

            @Override
            public void onFailure(Call<List<Word>> call, Throwable t) {
            }
        });
    }

    private void setWordDefinition() {
        phonetic.setText(phoneticOfWord);
        wordtype.setText("(" + typeOfWord + ")");
        definition.setText(defOfWord);
        addWordToList(defOfWord);
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
                Toast.makeText(Learn.this, "You've already learned this word!", Toast.LENGTH_SHORT).show();
            }
        }
        if (indexList > 4) {
            Toast.makeText(Learn.this, "You've reached 5/5 words!", Toast.LENGTH_SHORT).show();
            btn1.setVisibility(View.INVISIBLE);
            btn4.setVisibility(View.VISIBLE);
        }
    }

    private void getVoice() {
        WordService.wordService.getWord(word.getText().toString()).enqueue(new Callback<List<Word>>() {
            @Override
            public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                List<Word> word = response.body();
                if (word != null) {
                    voiceURL = word.get(0).phonetics.get(0).audio;
                }
            }

            @Override
            public void onFailure(Call<List<Word>> call, Throwable t) {
            }
        });
    }

    private void playVoice() {
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