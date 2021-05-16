package com.example.vocabapp.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sense {
    @SerializedName("crossReferenceMarkers")
    private String[] crossReferenceMarkers = null;

    @SerializedName("crossReferences")
    private List<CrossReferencesList> crossReferences = null;

    @SerializedName("definitions")
    private String[] definitions = null;

    @SerializedName("domains")
    private List<Domain> domains = null;

    @SerializedName("examples")
    private List<ExamplesList> examples = null;

    @SerializedName("id")
    private String id = null;

    @SerializedName("notes")
    private List<CategorizedTextList> notes = null;

    @SerializedName("pronunciations")
    private List<PronunciationsList> pronunciations = null;

    @SerializedName("regions")
    private List<Region> regions = null;

    @SerializedName("registers")
    private List<Register> registers = null;

    @SerializedName("subsenses")
    private List<Sense> subsenses = new ArrayList<Sense>();

    @SerializedName("translations")
    private List<TranslationsList> translations = null;

    @SerializedName("variantForms")
    private List<VariantFormsList> variantForms = null;

    public Sense crossReferenceMarkers(String[] crossReferenceMarkers) {
        this.crossReferenceMarkers = crossReferenceMarkers;
        return this;
    }

    /**
     * A grouping of crossreference notes.
     * @return crossReferenceMarkers
     **/
    public String[] getCrossReferenceMarkers() {
        return crossReferenceMarkers;
    }

    public void setCrossReferenceMarkers(String[] crossReferenceMarkers) {
        this.crossReferenceMarkers = crossReferenceMarkers;
    }

    public Sense crossReferences(List<CrossReferencesList> crossReferences) {
        this.crossReferences = crossReferences;
        return this;
    }

    /**
     * Get crossReferences
     * @return crossReferences
     **/
    public List<CrossReferencesList> getCrossReferences() {
        return crossReferences;
    }

    public void setCrossReferences(List<CrossReferencesList> crossReferences) {
        this.crossReferences = crossReferences;
    }

    public Sense definitions(String[] definitions) {
        this.definitions = definitions;
        return this;
    }

    /**
     * A list of statements of the exact meaning of a word
     * @return definitions
     **/
    public String[] getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String[] definitions) {
        this.definitions = definitions;
    }

    public Sense domains(List<Domain> domains) {
        this.domains = domains;
        return this;
    }

    /**
     * A subject, discipline, or branch of knowledge particular to the Sense
     * @return domains
     **/
    public List<Domain> getDomains() {
        return domains;
    }

    public void setDomains(List<Domain> domains) {
        this.domains = domains;
    }

    public Sense examples(List<ExamplesList> examples) {
        this.examples = examples;
        return this;
    }

    /**
     * Get examples
     * @return examples
     **/
    public List<ExamplesList> getExamples() {
        return examples;
    }

    public void setExamples(List<ExamplesList> examples) {
        this.examples = examples;
    }

    public Sense id(String id) {
        this.id = id;
        return this;
    }

    /**
     * The id of the sense that is required for the delete procedure
     * @return id
     **/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Sense notes(List<CategorizedTextList> notes) {
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

    public Sense pronunciations(List<PronunciationsList> pronunciations) {
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

    public Sense regions(List<Region> regions) {
        this.regions = regions;
        return this;
    }

    /**
     * A particular area in which the Sense occurs, e.g. 'Great Britain'
     * @return regions
     **/
    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    public Sense registers(List<Register> registers) {
        this.registers = registers;
        return this;
    }

    /**
     * A level of language usage, typically with respect to formality. e.g. 'offensive', 'informal'
     * @return registers
     **/
    public List<Register> getRegisters() {
        return registers;
    }

    public void setRegisters(List<Register> registers) {
        this.registers = registers;
    }

    public Sense subsenses(List<Sense> subsenses) {
        this.subsenses = subsenses;
        return this;
    }

    public Sense addSubsensesItem(Sense subsensesItem) {
        this.subsenses.add(subsensesItem);
        return this;
    }

    /**
     * Ordered list of subsenses of a sense
     * @return subsenses
     **/
    public List<Sense> getSubsenses() {
        return subsenses;
    }

    public void setSubsenses(List<Sense> subsenses) {
        this.subsenses = subsenses;
    }

    public Sense translations(List<TranslationsList> translations) {
        this.translations = translations;
        return this;
    }

    /**
     * Get translations
     * @return translations
     **/
    public List<TranslationsList> getTranslations() {
        return translations;
    }

    public void setTranslations(List<TranslationsList> translations) {
        this.translations = translations;
    }

    public Sense variantForms(List<VariantFormsList> variantForms) {
        this.variantForms = variantForms;
        return this;
    }

    /**
     * Various words that are used interchangeably depending on the context, e.g 'duck' and 'duck boat'
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
        Sense sense = (Sense) o;
        return Objects.equals(this.crossReferenceMarkers, sense.crossReferenceMarkers) &&
                Objects.equals(this.crossReferences, sense.crossReferences) &&
                Objects.equals(this.definitions, sense.definitions) &&
                Objects.equals(this.domains, sense.domains) &&
                Objects.equals(this.examples, sense.examples) &&
                Objects.equals(this.id, sense.id) &&
                Objects.equals(this.notes, sense.notes) &&
                Objects.equals(this.pronunciations, sense.pronunciations) &&
                Objects.equals(this.regions, sense.regions) &&
                Objects.equals(this.registers, sense.registers) &&
                Objects.equals(this.subsenses, sense.subsenses) &&
                Objects.equals(this.translations, sense.translations) &&
                Objects.equals(this.variantForms, sense.variantForms);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(crossReferenceMarkers, crossReferences, definitions, domains, examples, id, notes, pronunciations, regions, registers, subsenses, translations, variantForms);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Sense {\n");

        sb.append("    crossReferenceMarkers: ").append(toIndentedString(crossReferenceMarkers)).append("\n");
        sb.append("    crossReferences: ").append(toIndentedString(crossReferences)).append("\n");
        sb.append("    definitions: ").append(toIndentedString(definitions)).append("\n");
        sb.append("    domains: ").append(toIndentedString(domains)).append("\n");
        sb.append("    examples: ").append(toIndentedString(examples)).append("\n");
        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
        sb.append("    pronunciations: ").append(toIndentedString(pronunciations)).append("\n");
        sb.append("    regions: ").append(toIndentedString(regions)).append("\n");
        sb.append("    registers: ").append(toIndentedString(registers)).append("\n");
        sb.append("    subsenses: ").append(toIndentedString(subsenses)).append("\n");
        sb.append("    translations: ").append(toIndentedString(translations)).append("\n");
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
