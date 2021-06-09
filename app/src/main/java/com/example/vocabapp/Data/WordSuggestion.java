package com.example.vocabapp.Data;

import android.os.Parcel;

import androidx.annotation.InspectableProperty;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.firebase.database.IgnoreExtraProperties;

public class WordSuggestion implements SearchSuggestion {
    private String mWord;
    private boolean mIsHistory = false;
    public WordSuggestion(String suggestion) {this.mWord = suggestion.toLowerCase();}
    public WordSuggestion(String suggestion, boolean isHistory){this.mWord = suggestion; this.mIsHistory = isHistory;}

    public boolean getIsHistory(){return this.mIsHistory;}
    public void setIsHistory(boolean isHistory){this.mIsHistory = isHistory;}

    @Override
    public String getBody() {
        return this.mWord;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WordSuggestion> CREATOR = new Creator<WordSuggestion>() {
        @Override
        public WordSuggestion createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public WordSuggestion[] newArray(int size) {
            return new WordSuggestion[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mWord);
        dest.writeInt(mIsHistory?1:0);
    }
}

