package com.example.vocabapp.DatabaseHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vocabapp.Data.WordSuggestion;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public List<WordSuggestion> getSuggestions() {
        List<WordSuggestion> list = new ArrayList<>();
        open();
        Cursor cursor = database.rawQuery("SELECT DISTINCT word FROM word", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WordSuggestion wordSuggestion = new WordSuggestion(cursor.getString(0));
            list.add(wordSuggestion);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return list;
    }
}
