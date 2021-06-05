package com.example.vocabapp.API;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private OkHttpClient httpClient;
    private Retrofit retrofit;
    private final Map<Class, Object> interfaceCache = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> interfaceClass) {
        T cachedInterface = (T) interfaceCache.get(interfaceClass);
        if (cachedInterface == null) {
            cachedInterface = retrofit().create(interfaceClass);
            interfaceCache.put(interfaceClass, cachedInterface);
        }
        return cachedInterface;
    }

    public Retrofit retrofit() {
        if (this.retrofit == null) {
            httpClient = new OkHttpClient.Builder().build();

            retrofit = new Retrofit.Builder()
                    .client(httpClient)
                    .baseUrl("https://od-api.oxforddictionaries.com:443/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
                    .build();
        }
        return retrofit;
    }
}
