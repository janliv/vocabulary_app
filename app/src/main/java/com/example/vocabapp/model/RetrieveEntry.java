package com.example.vocabapp.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RetrieveEntry {
    @SerializedName("metadata")
    private Object metadata = null;

    @SerializedName("results")
    private List<HeadwordEntry> results = new ArrayList<HeadwordEntry>();

    public RetrieveEntry metadata(Object metadata) {
        this.metadata = metadata;
        return this;
    }

    /**
     * Additional Information provided by OUP
     * @return metadata
     **/
    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public RetrieveEntry results(List<HeadwordEntry> results) {
        this.results = results;
        return this;
    }

    public RetrieveEntry addResultsItem(HeadwordEntry resultsItem) {
        this.results.add(resultsItem);
        return this;
    }

    /**
     * A list of entries and all the data related to them
     * @return results
     **/
    public List<HeadwordEntry> getResults() {
        return results;
    }

    public void setResults(List<HeadwordEntry> results) {
        this.results = results;
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
        RetrieveEntry retrieveEntry = (RetrieveEntry) o;
        return Objects.equals(this.metadata, retrieveEntry.metadata) &&
                Objects.equals(this.results, retrieveEntry.results);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(metadata, results);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class RetrieveEntry {\n");

        sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
        sb.append("    results: ").append(toIndentedString(results)).append("\n");
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
