package com.example.vocabapp.InternetConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {
    private Context context;
    private static Connection instance;

    private Connection(Context context) {
        this.context = context;
    }

    public static Connection getInstance(Context context) {
        if (instance == null) {
            instance = new Connection(context);
        }
        return instance;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting())
            return true;
        return false;
    }
}
