package com.example.vocabapp.Data.DataSource;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.vocabapp.model.RetrieveEntry;
import com.google.gson.Gson;

import io.reactivex.Observable;

public class DiskDataSource implements DataSource {

    private final SharedPreferences sharedPreferences;

    public DiskDataSource(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Observable<RetrieveEntry> getData(String word) {
        return Observable.create(emitter -> {
            String string = sharedPreferences.getString(word, "");
            if (!string.equals("")) {
                Gson gson = new Gson();
                RetrieveEntry retrieveEntry = gson.fromJson(string, RetrieveEntry.class);
                emitter.onNext(retrieveEntry);
                Log.d("DISK", "get from disk");
            }
            emitter.onComplete();
        });
    }

    public void saveData(String word, RetrieveEntry retrieveEntry) {
        if (retrieveEntry != null) {
            Gson gson = new Gson();
            String str = gson.toJson(retrieveEntry);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(word, str);
            if (editor.commit())
                Log.d("savedata", word + str);
        }
    }
}
