package com.example.vocabapp.Data.DataSource;

import com.example.vocabapp.model.RetrieveEntry;

import io.reactivex.Observable;

public class DataRepository implements  DataSource{
    private final MemoryDataSource memoryDataSource;
    private final DiskDataSource diskDataSource;
    private final NetworkDataSource networkDataSource;

    public DataRepository(MemoryDataSource memoryDataSource, DiskDataSource diskDataSource,NetworkDataSource networkDataSource){
        this.diskDataSource = diskDataSource;
        this.memoryDataSource = memoryDataSource;
        this.networkDataSource = networkDataSource;
    }

    @Override
    public Observable<RetrieveEntry> getData(String word) {
        Observable<RetrieveEntry> memory = getDataFromMemory(word);
        Observable<RetrieveEntry> disk = getDataFromDisk(word);
        Observable<RetrieveEntry> network = getDataFromNetwork(word);
        return Observable.concat(memory,disk,network).firstElement().toObservable();
    }

    private Observable<RetrieveEntry> getDataFromMemory(String word){
        return networkDataSource.getData(word).doOnNext(retrieveEntry -> {
            diskDataSource.saveData(word, retrieveEntry);
            memoryDataSource.cacheInMemory(word, retrieveEntry);
        });
    }

    private Observable<RetrieveEntry> getDataFromDisk(String word){
        return diskDataSource.getData(word).doOnNext(retrieveEntry -> memoryDataSource.cacheInMemory(word, retrieveEntry));
    }

    private Observable<RetrieveEntry> getDataFromNetwork(String word){
        return memoryDataSource.getData(word);
    }
}
