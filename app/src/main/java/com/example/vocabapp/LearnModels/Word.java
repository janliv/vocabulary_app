package com.example.vocabapp.LearnModels;

import java.util.List;

public class Word {
    public List<Meanings> meanings;
    public List<Phonetics> phonetics;

    public List<Meanings> getMeanings() {
        return meanings;
    }

    public List<Phonetics> getPhonetics() {
        return phonetics;
    }
}
