package com.example.vocabapp.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HeadwordEntry {
    @SerializedName("id")
    private String id = null;

    @SerializedName("language")
    private String language = null;

    @SerializedName("lexicalEntries")
    private List<LexicalEntry> lexicalEntries = new ArrayList<LexicalEntry>();

    @SerializedName("pronunciations")
    private List<PronunciationsList> pronunciations = null;

    @SerializedName("type")
    private String type = null;

    @SerializedName("word")
    private String word = null;

    public HeadwordEntry id(String id) {
        this.id = id;
        return this;
    }

    /**
     * The identifier of a word
     * @return id
     **/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HeadwordEntry language(String language) {
        this.language = language;
        return this;
    }

    /**
     * IANA language code
     * @return language
     **/
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public HeadwordEntry lexicalEntries(List<LexicalEntry> lexicalEntries) {
        this.lexicalEntries = lexicalEntries;
        return this;
    }

    public HeadwordEntry addLexicalEntriesItem(LexicalEntry lexicalEntriesItem) {
        this.lexicalEntries.add(lexicalEntriesItem);
        return this;
    }

    /**
     * A grouping of various senses in a specific language, and a lexical category that relates to a word
     * @return lexicalEntries
     **/
    public List<LexicalEntry> getLexicalEntries() {
        return lexicalEntries;
    }

    public void setLexicalEntries(List<LexicalEntry> lexicalEntries) {
        this.lexicalEntries = lexicalEntries;
    }

    public HeadwordEntry pronunciations(List<PronunciationsList> pronunciations) {
        this.pronunciations = pronunciations;
        return this;
    }

    /**
     * Get pronunciations
     * @return pronunciations
     **/
    public List<PronunciationsList> getPronunciations() {
        return pronunciations;
    }

    public void setPronunciations(List<PronunciationsList> pronunciations) {
        this.pronunciations = pronunciations;
    }

    public HeadwordEntry type(String type) {
        this.type = type;
        return this;
    }

    /**
     * The json object type. Could be 'headword', 'inflection' or 'phrase'
     * @return type
     **/
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HeadwordEntry word(String word) {
        this.word = word;
        return this;
    }

    /**
     * A given written or spoken realisation of a an entry, lowercased.
     * @return word
     **/
    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeadwordEntry headwordEntry = (HeadwordEntry) o;
        return Objects.equals(this.id, headwordEntry.id) &&
                Objects.equals(this.language, headwordEntry.language) &&
                Objects.equals(this.lexicalEntries, headwordEntry.lexicalEntries) &&
                Objects.equals(this.pronunciations, headwordEntry.pronunciations) &&
                Objects.equals(this.type, headwordEntry.type) &&
                Objects.equals(this.word, headwordEntry.word);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(id, language, lexicalEntries, pronunciations, type, word);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class HeadwordEntry {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    language: ").append(toIndentedString(language)).append("\n");
        sb.append("    lexicalEntries: ").append(toIndentedString(lexicalEntries)).append("\n");
        sb.append("    pronunciations: ").append(toIndentedString(pronunciations)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    word: ").append(toIndentedString(word)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
