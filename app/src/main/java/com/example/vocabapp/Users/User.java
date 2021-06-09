package com.example.vocabapp.Users;

import com.example.vocabapp.Data.WordSuggestion;
import com.example.vocabapp.model.Word;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;


//@IgnoreExtraProperties
public class User {
    private int exp;
    private String urlPhoto;
    private List<Object> wordLearned;
    private List<Object> wordSeen;
    private List<Object> wordSearched;

    public User() {
    }


    public User(int exp, String urlPhoto, List<Object> wordLearned, List<Object> wordSeen, List<Object> wordSearched) {

        this.exp = exp;
        this.urlPhoto = urlPhoto;
        this.wordLearned = wordLearned;
        this.wordSeen = wordSeen;
        this.wordSearched = wordSearched;
    }

    public int getExp() {
        return exp;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public List<Object> getWordLearned() {
        return wordLearned;
    }

    public List<Object> getWordSearched() {
        return wordSearched;
    }

    public List<Object> getWordSeen() {
        return wordSeen;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public void setWordLearned(List<Object> wordLearned) {
        this.wordLearned = wordLearned;
    }

    public void setWordSeen(List<Object> wordSeen) {
        this.wordSeen = wordSeen;
    }

    public void setWordSearched(List<Object> wordSearched) {
        this.wordSearched = wordSearched;
    }
}
