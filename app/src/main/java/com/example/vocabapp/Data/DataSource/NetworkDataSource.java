package com.example.vocabapp.Data.DataSource;

import android.content.Context;

import com.example.vocabapp.API.ApiClient;
import com.example.vocabapp.API.DictionaryEntriesApi;
import com.example.vocabapp.InternetConnection.Connection;
import com.example.vocabapp.model.RetrieveEntry;

import io.reactivex.Emitter;
import io.reactivex.Observable;

public class NetworkDataSource implements DataSource {
    private Context context;
     public NetworkDataSource (Context context){
         this.context = context;
     }
    @Override
    public Observable<RetrieveEntry> getData(String word) {
        if(!Connection.getInstance(context).isOnline()){
            return Observable.create(Emitter::onComplete);
        }
            ApiClient apiClient = new ApiClient();
            DictionaryEntriesApi entriesApi = apiClient.get(DictionaryEntriesApi.class);
            return entriesApi.getDictionaryEntries("en-us", word, "application/json", "0b61a8b1", "aae13662958f2a69eb82099315c1375d");
    }
}
