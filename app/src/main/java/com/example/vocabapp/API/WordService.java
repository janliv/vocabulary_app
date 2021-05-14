package com.example.vocabapp.API;

import com.example.vocabapp.model.Word;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WordService {
    @GET("word")
    Call<List<Word>> getAllWords();
}
