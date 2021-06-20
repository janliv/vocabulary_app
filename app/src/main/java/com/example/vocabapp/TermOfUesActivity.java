package com.example.vocabapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class TermOfUesActivity extends AppCompatActivity {
    private ImageButton backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_of_ues);
        backButton = findViewById(R.id.image_back_button);

        backButton.setOnClickListener(v->finish());
    }
}