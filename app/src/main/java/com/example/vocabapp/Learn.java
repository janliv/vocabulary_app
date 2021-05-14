package com.example.vocabapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vocabapp.API.WordService;
import com.example.vocabapp.model.Word;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hanks.htextview.base.HTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Learn extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;

    private HTextView vocab;

    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE = 0;
    private GestureDetector gestureDetector;

    private GifImageView swipeup;

    private static final String WORD_URL = "https://random-words-api.vercel.app/";

    // Override on touch event
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                float valueX = y2 - x1;
                float valueY = y2 - y1;

                if (Math.abs(valueX) > MIN_DISTANCE) {
                    if (y2 <= y1) {
                        callApi(); // Top swiped to call API
                    }
                }
        }

        return super.onTouchEvent(event);
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

        // Initialize gesture detector
        this.gestureDetector = new GestureDetector(this, this);

        // Random word
        vocab = findViewById(R.id.word);

        // Swipe up gif
        swipeup = findViewById(R.id.swipe_up);
    }

    private void callApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WORD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WordService wordService = retrofit.create(WordService.class);
        Call<List<Word>> call = wordService.getAllWords();
        call.enqueue(new Callback<List<Word>>() {
            @Override
            public void onResponse(Call<List<Word>> call, Response<List<Word>> response) {
                vocab.animateText(response.body().toString().replaceFirst("]", ""));
            }

            @Override
            public void onFailure(Call<List<Word>> call, Throwable t) {
                vocab.animateText("Please wait a few seconds!");
            }
        });
    }


    // Swipe up gesture
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

}