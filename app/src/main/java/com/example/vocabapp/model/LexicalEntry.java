package com.example.vocabapp.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LexicalEntry {
    @SerializedName("derivativeOf")
    private ArrayOfRelatedEntries derivativeOf = null;

    @SerializedName("entries")
    private List<Entry> entries = new ArrayList<>();

    @SerializedName("grammaticalFeatures")
    private GrammaticalFeaturesList grammaticalFeatures = null;

    @SerializedName("language")
    private String language = null;

    @SerializedName("lexicalCategory")
    private LexicalCategory lexicalCategory = null;

    @SerializedName("notes")
    private CategorizedTextList notes = null;

    @SerializedName("pronunciations")
    private List<PronunciationsList> pronunciations = null;

    @SerializedName("text")
    private String text = null;

    @SerializedName("variantForms")
    private VariantFormsList variantForms = null;

    public LexicalEntry derivativeOf(ArrayOfRelatedEntries derivativeOf) {
        this.derivativeOf = derivativeOf;
        return this;
    }

    /**
     * Other words from which this one derives
     * @return derivativeOf
     **/
    public ArrayOfRelatedEntries getDerivativeOf() {
        return derivativeOf;
    }

    public void setDerivativeOf(ArrayOfRelatedEntries derivativeOf) {
        this.derivativeOf = derivativeOf;
    }

    public LexicalEntry entries(List<Entry> entries) {
        this.entries = entries;
        return this;
    }

    public LexicalEntry addEntriesItem(Entry entriesItem) {
        this.entries.add(entriesItem);
        return this;
    }

    /**
     * Get entries
     * @return entries
     **/
    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public LexicalEntry grammaticalFeatures(GrammaticalFeaturesList grammaticalFeatures) {
        this.grammaticalFeatures = grammaticalFeatures;
        return this;
    }

    /**
     * Get grammaticalFeatures
     * @return grammaticalFeatures
     **/
    public GrammaticalFeaturesList getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    public void setGrammaticalFeatures(GrammaticalFeaturesList grammaticalFeatures) {
        this.grammaticalFeatures = grammaticalFeatures;
    }

    public LexicalEntry language(String language) {
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

    public LexicalEntry lexicalCategory(LexicalCategory lexicalCategory) {
        this.lexicalCategory = lexicalCategory;
        return this;
    }

    /**
     * A linguistic category of words (or more precisely lexical items), generally defined by the syntactic or morphological behaviour of the lexical item in question, such as noun or verb
     * @return lexicalCategory
     **/
    public LexicalCategory getLexicalCategory() {
        return lexicalCategory;
    }

    public void setLexicalCategory(LexicalCategory lexicalCategory) {
        this.lexicalCategory = lexicalCategory;
    }

    public LexicalEntry notes(CategorizedTextList notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Get notes
     * @return notes
     **/
    public CategorizedTextList getNotes() {
        return notes;
    }

    public void setNotes(CategorizedTextList notes) {
        this.notes = notes;
    }

    public LexicalEntry pronunciations(List<PronunciationsList> pronunciations) {
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

    public LexicalEntry text(String text) {
        this.text = text;
        return this;
    }

    /**
     * A given written or spoken realisation of a an entry.
     * @return text
     **/
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LexicalEntry variantForms(VariantFormsList variantForms) {
        this.variantForms = variantForms;
        return this;
    }

    /**
     * Various words that are used interchangeably depending on the context, e.g 'a' and 'an'
     * @return variantForms
     **/
    public VariantFormsList getVariantForms() {
        return variantForms;
    }

    public void setVariantForms(VariantFormsList variantForms) {
        this.variantForms = variantForms;
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
        LexicalEntry lexicalEntry = (LexicalEntry) o;
        return Objects.equals(this.derivativeOf, lexicalEntry.derivativeOf) &&
                Objects.equals(this.entries, lexicalEntry.entries) &&
                Objects.equals(this.grammaticalFeatures, lexicalEntry.grammaticalFeatures) &&
                Objects.equals(this.language, lexicalEntry.language) &&
                Objects.equals(this.lexicalCategory, lexicalEntry.lexicalCategory) &&
                Objects.equals(this.notes, lexicalEntry.notes) &&
                Objects.equals(this.pronunciations, lexicalEntry.pronunciations) &&
                Objects.equals(this.text, lexicalEntry.text) &&
                Objects.equals(this.variantForms, lexicalEntry.variantForms);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(derivativeOf, entries, grammaticalFeatures, language, lexicalCategory, notes, pronunciations, text, variantForms);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class LexicalEntry {\n");

        sb.append("    derivativeOf: ").append(toIndentedString(derivativeOf)).append("\n");
        sb.append("    entries: ").append(toIndentedString(entries)).append("\n");
        sb.append("    grammaticalFeatures: ").append(toIndentedString(grammaticalFeatures)).append("\n");
        sb.append("    language: ").append(toIndentedString(language)).append("\n");
        sb.append("    lexicalCategory: ").append(toIndentedString(lexicalCategory)).append("\n");
        sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
        sb.append("    pronunciations: ").append(toIndentedString(pronunciations)).append("\n");
        sb.append("    text: ").append(toIndentedString(text)).append("\n");
        sb.append("    variantForms: ").append(toIndentedString(variantForms)).append("\n");
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
