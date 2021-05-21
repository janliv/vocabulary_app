package com.example.vocabapp.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Entry {
    @SerializedName("etymologies")
    private String[] etymologies = null;

    @SerializedName("grammaticalFeatures")
    private List<GrammaticalFeaturesList> grammaticalFeatures = null;

    @SerializedName("homographNumber")
    private String homographNumber = null;

    @SerializedName("notes")
    private List<CategorizedTextList> notes = null;

    @SerializedName("pronunciations")
    private List<PronunciationsList> pronunciations = null;

    @SerializedName("senses")
    private List<Sense> senses = new ArrayList<Sense>();

    @SerializedName("variantForms")
    private List<VariantFormsList> variantForms = null;

    public Entry etymologies(String[] etymologies) {
        this.etymologies = etymologies;
        return this;
    }

    /**
     * The origin of the word and the way in which its meaning has changed throughout history
     * @return etymologies
     **/
    public String[] getEtymologies() {
        return etymologies;
    }

    public void setEtymologies(String[] etymologies) {
        this.etymologies = etymologies;
    }

    public Entry grammaticalFeatures(List<GrammaticalFeaturesList> grammaticalFeatures) {
        this.grammaticalFeatures = grammaticalFeatures;
        return this;
    }

    /**
     * Get grammaticalFeatures
     * @return grammaticalFeatures
     **/
    public List<GrammaticalFeaturesList> getGrammaticalFeatures() {
        return grammaticalFeatures;
    }

    public void setGrammaticalFeatures(List<GrammaticalFeaturesList> grammaticalFeatures) {
        this.grammaticalFeatures = grammaticalFeatures;
    }

    public Entry homographNumber(String homographNumber) {
        this.homographNumber = homographNumber;
        return this;
    }

    /**
     * Identifies the homograph grouping. The last two digits identify different entries of the same homograph. The first one/two digits identify the homograph number.
     * @return homographNumber
     **/
    public String getHomographNumber() {
        return homographNumber;
    }

    public void setHomographNumber(String homographNumber) {
        this.homographNumber = homographNumber;
    }

    public Entry notes(List<CategorizedTextList> notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Get notes
     * @return notes
     **/
    public List<CategorizedTextList> getNotes() {
        return notes;
    }

    public void setNotes(List<CategorizedTextList> notes) {
        this.notes = notes;
    }

    public Entry pronunciations(List<PronunciationsList> pronunciations) {
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

    public Entry senses(List<Sense> senses) {
        this.senses = senses;
        return this;
    }

    public Entry addSensesItem(Sense sensesItem) {
        this.senses.add(sensesItem);
        return this;
    }

    /**
     * Complete list of senses
     * @return senses
     **/
    public List<Sense> getSenses() {
        return senses;
    }

    public void setSenses(List<Sense> senses) {
        this.senses = senses;
    }

    public Entry variantForms(List<VariantFormsList> variantForms) {
        this.variantForms = variantForms;
        return this;
    }

    /**
     * Various words that are used interchangeably depending on the context, e.g 'a' and 'an'
     * @return variantForms
     **/
    public List<VariantFormsList> getVariantForms() {
        return variantForms;
    }

    public void setVariantForms(List<VariantFormsList> variantForms) {
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
        Entry entry = (Entry) o;
        return Objects.equals(this.etymologies, entry.etymologies) &&
                Objects.equals(this.grammaticalFeatures, entry.grammaticalFeatures) &&
                Objects.equals(this.homographNumber, entry.homographNumber) &&
                Objects.equals(this.notes, entry.notes) &&
                Objects.equals(this.pronunciations, entry.pronunciations) &&
                Objects.equals(this.senses, entry.senses) &&
                Objects.equals(this.variantForms, entry.variantForms);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(etymologies, grammaticalFeatures, homographNumber, notes, pronunciations, senses, variantForms);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Entry {\n");

        sb.append("    etymologies: ").append(toIndentedString(etymologies)).append("\n");
        sb.append("    grammaticalFeatures: ").append(toIndentedString(grammaticalFeatures)).append("\n");
        sb.append("    homographNumber: ").append(toIndentedString(homographNumber)).append("\n");
        sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
        sb.append("    pronunciations: ").append(toIndentedString(pronunciations)).append("\n");
        sb.append("    senses: ").append(toIndentedString(senses)).append("\n");
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
