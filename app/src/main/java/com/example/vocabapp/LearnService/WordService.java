package com.example.vocabapp.LearnService;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.vocabapp.Learn;
import com.example.vocabapp.LearnModels.Word;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WordService {

    Gson gson = new GsonBuilder()
            .setDateFormat("MM-dd-yyyy HH:mm:ss")
            .create();

    WordService wordService = new Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(WordService.class);

    @GET("api/v2/entries/en_US/{word}")
    Call<List<Word>> getWord(@Path("word") String _word);
}
