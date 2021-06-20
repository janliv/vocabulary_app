package com.example.vocabapp.OxfordDictionary;

import com.example.vocabapp.model.Entry;
import com.example.vocabapp.model.ExamplesList;
import com.example.vocabapp.model.PronunciationsList;
import com.example.vocabapp.model.Sense;
import com.example.vocabapp.model.SynonymsAntonyms;

import java.util.List;

public class Definition {
    private final String category;
    private final String word;
    private final String etymology;
    private final String definition;
    private final String example;
    private final String pronunciation;
    private final String pronunciationUrl;
    private StringBuilder synonyms = new StringBuilder();
    private StringBuilder antonyms = new StringBuilder();

    public Definition(String category, String word, Entry entry, Sense s) {
        this.category = category;
        this.word = word;

        List<ExamplesList> examples = s.getExamples();
        this.example = examples != null && examples.size() > 0 ? examples.get(0).getText() : null;

        List<PronunciationsList> pronunciations;
        if (s.getPronunciations() != null) {
            pronunciations = s.getPronunciations();
        } else {
            pronunciations = entry.getPronunciations();
        }
        this.pronunciation = pronunciations != null && pronunciations.size() > 0 ? pronunciations.get(0).getPhoneticSpelling() : null;
        this.pronunciationUrl = pronunciations != null && pronunciations.size() > 0 ? pronunciations.get(1).getAudioFile() : null;

        String[] etymologies = entry.getEtymologies();
        this.etymology = etymologies != null && etymologies.length > 0 ? etymologies[0] : null;

        String[] definitions = s.getDefinitions();
        this.definition = definitions != null && definitions.length > 0 ? definitions[0] : null;

        if (s.getAntonyms() != null && s.getAntonyms().size() > 0) {
            for (SynonymsAntonyms antonyms : s.getAntonyms())
                this.antonyms.append(antonyms.getText()).append(", ");
        } else this.antonyms = null;

        if (s.getSynonyms() != null && s.getSynonyms().size() > 0) {
            for (SynonymsAntonyms synonyms : s.getSynonyms())
                this.synonyms.append(synonyms.getText()).append(", ");
        } else this.synonyms = null;

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
        if (example != null)
            return "\t> example: " + example;
        return null;
    }

    public String getPronunciation() {
        return "/" + this.pronunciation + "/";
    }

    public String getAntonyms() {
        if (antonyms == null)
            return null;
        return "\t> antonyms: " + antonyms.delete(antonyms.length() - 2, antonyms.length()).toString();
    }

    public String getSynonyms() {
        if (synonyms == null)
            return null;
        return "\t\t> synonyms: " + synonyms.delete(synonyms.length() - 2, synonyms.length()).toString();
    }

    public String getPronunciationUrl() {
        return this.pronunciationUrl;
    }
}
