package com.example.vocabapp.OxfordDictionary;

import com.example.vocabapp.model.Entry;
import com.example.vocabapp.model.ExamplesList;
import com.example.vocabapp.model.PronunciationsList;
import com.example.vocabapp.model.Sense;

import java.util.List;

public class Definition {
    private final String category;
    private final String word;
    private final String etymology;
    private final String definition;
    private final String example;
    private final String pronunciation;

    public Definition(String category, String word, Entry entry, Sense s) {
        this.category = category;
        this.word = word;

        List<ExamplesList> examples = s.getExamples();
        this.example = examples != null && examples.size() > 0 ? examples.get(0).getText() : null;

        if (s.getPronunciations() != null) {
            List<PronunciationsList> pronunciations = s.getPronunciations();
            this.pronunciation = pronunciations != null && pronunciations.size() > 0 ? pronunciations.get(0).getPhoneticSpelling() : null;
        } else {
            List<PronunciationsList> pronunciations = entry.getPronunciations();
            this.pronunciation = pronunciations != null && pronunciations.size() > 0 ? pronunciations.get(0).getPhoneticSpelling() : null;
        }

        String[] etymologies = entry.getEtymologies();
        this.etymology = etymologies != null && etymologies.length > 0 ? etymologies[0] : null;

        String[] definitions = s.getDefinitions();
        this.definition = definitions != null && definitions.length > 0 ? definitions[0] : null;
    }

    public String getCategory() {
        return category;
    }

    public String getWord() {
        return word;
    }

    public String getEtymology() {
        return etymology;
    }

    public String getDefinition() {
        return definition;
    }

    public String getExample() {
        return example;
    }

    public String getPronunciation() {
        return "/" + this.pronunciation + "/";
    }
}
