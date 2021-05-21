package com.example.vocabapp.Data.DataSource;

import com.example.vocabapp.model.RetrieveEntry;

import io.reactivex.Observable;

public class MemoryDataSource implements DataSource{
    private String word;
    private RetrieveEntry retrieveEntry;
    @Override
    public Observable<RetrieveEntry> getData(String word) {
        return Observable.create(emitter->{
            if(retrieveEntry!=null&& this.word.equals(word))
                emitter.onNext(retrieveEntry);
            emitter.onComplete();
        });
    }


    public void cacheInMemory(String word, RetrieveEntry retrieveEntry){
        this.word = word;
        this.retrieveEntry = retrieveEntry;
    }
}
