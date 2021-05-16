package com.example.vocabapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.vocabapp.API.ApiClient;
import com.example.vocabapp.API.DictionaryEntriesApi;
import com.example.vocabapp.OxfordDictionary.Definition;
import com.example.vocabapp.OxfordDictionary.DefinitionRenderer;
import com.example.vocabapp.R;
import com.example.vocabapp.Search;
import com.example.vocabapp.model.Entry;
import com.pedrogomez.renderers.ListAdapteeCollection;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class SearchResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DictionaryEntriesApi entriesApi;

    private String wordSearch;
    private String pronunciation;
    private TextView wordHeaderTextView;
    private TextView pronunciationTextView;
    private ImageButton backImageButton;
    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);



        Intent intent = getIntent();
        wordSearch = intent.getExtras().getString(Search.WORD_SEARCH);
        // pronunciation = intent.getExtras().getString(Search.PRONUNCIATION);


        backImageButton = findViewById(R.id.back_image_button);
        wordHeaderTextView = findViewById(R.id.word_text_view_header);
        // pronunciationTextView = findViewById(R.id.pronunciation_text_view);

        wordHeaderTextView.setText(wordSearch);
        // pronunciationTextView.setText(pronunciation);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

    }







}

