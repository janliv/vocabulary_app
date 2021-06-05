package com.example.vocabapp.Data.DataSource;

import com.example.vocabapp.API.ApiClient;
import com.example.vocabapp.API.DictionaryEntriesApi;
import com.example.vocabapp.model.RetrieveEntry;
import com.google.gson.internal.$Gson$Preconditions;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkDataSource implements DataSource {
    @Override
    public Observable<RetrieveEntry> getData(String word) {
        ApiClient apiClient = new ApiClient();
        DictionaryEntriesApi entriesApi = apiClient.get(DictionaryEntriesApi.class);
        return entriesApi.getDictionaryEntries("en-us", word, "application/json", "0b61a8b1", "aae13662958f2a69eb82099315c1375d");
    }
}
