package com.example.vocabapp.Data.DataSource;

import android.content.Context;

import com.example.vocabapp.model.RetrieveEntry;

import io.reactivex.Observable;

public interface DataSource {
    Observable<RetrieveEntry> getData(String word);
}
