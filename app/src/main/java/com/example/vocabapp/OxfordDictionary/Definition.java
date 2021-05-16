package com.example.vocabapp.OxfordDictionary;

import com.example.vocabapp.model.Entry;
import com.example.vocabapp.model.LexicalCategory;
import com.example.vocabapp.model.LexicalEntry;
import com.example.vocabapp.model.Sense;

public class Definition {
    private final String category;
    private final String word;
    private final String etymology;
    private final String definition;
    private final String example;

    public Definition(String category, String word, Entry entry, Sense s, String example) {
        this.category = category;
        this.word = word;
        this.example = example;

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

    public String getExample(){return example;}
}
