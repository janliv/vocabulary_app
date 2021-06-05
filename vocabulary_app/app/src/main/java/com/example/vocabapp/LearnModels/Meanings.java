package com.example.vocabapp.LearnModels;

import java.util.List;

public class Meanings {
    public String partOfSpeech;
    public List<Definitions> definitions;

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public List<Definitions> getDefinitions() {
        return definitions;
    }
}
