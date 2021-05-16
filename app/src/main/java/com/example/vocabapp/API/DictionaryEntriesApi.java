package com.example.vocabapp.API;

import com.example.vocabapp.model.RetrieveEntry;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DictionaryEntriesApi {
    @GET("entries/{source_lang}/{word_id}/definitions")
    Observable<RetrieveEntry> entriesSourceLangWordIdDefinitionsGet(
            @Path("source_lang") String sourceLang, @Path("word_id") String wordId, @Header("app_id") String appId, @Header("app_key") String appKey
    );

    @GET("entries/{source_lang}/{word_id}/examples")
    Observable<RetrieveEntry> entriesSourceLangWordIdExamplesGet(
            @Path("source_lang") String sourceLang, @Path("word_id") String wordId, @Header("app_id") String appId, @Header("app_key") String appKey
    );

    @GET("entries/{source_lang}/{word_id}/{filters}")
    Observable<RetrieveEntry> entriesSourceLangWordIdFiltersGet(
            @Path("source_lang") String sourceLang, @Path("word_id") String wordId, @Path("filters") List<String> filters, @Header("app_id") String appId, @Header("app_key") String appKey
    );

    @GET("entries/{source_lang}/{word_id}?strictMatch=false")
    Observable<RetrieveEntry> getDictionaryEntries(
            @Path("source_lang") String sourceLang, @Path("word_id") String wordId,@Header("Accept") String accept, @Header("app_id") String appId, @Header("app_key") String appKey
    );
    @GET("entries/{source_lang}/{word_id}/pronunciations")
    Observable<RetrieveEntry> entriesSourceLangWordIdPronunciationsGet(
            @Path("source_lang") String sourceLang, @Path("word_id") String wordId, @Header("app_id") String appId, @Header("app_key") String appKey
    );
    @GET("entries/{source_lang}/{word_id}/regions&#x3D;{region}")
    Observable<RetrieveEntry> entriesSourceLangWordIdRegionsregionGet(
            @Path("source_lang") String sourceLang, @Path("word_id") String wordId, @Path("region") String region, @Header("app_id") String appId, @Header("app_key") String appKey
    );

}
