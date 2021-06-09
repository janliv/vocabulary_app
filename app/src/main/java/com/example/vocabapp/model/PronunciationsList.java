package com.example.vocabapp.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class PronunciationsList {
    @SerializedName("audioFile")
    private String audioFile;

    @SerializedName("phoneticSpelling")
    private String phoneticSpelling;


    public String getPhoneticSpelling() {
        return this.phoneticSpelling;
    }

    public String getAudioFile() {
        return this.audioFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PronunciationsList {\n");
        sb.append("    audioFile: ").append(toIndentedString(audioFile)).append("\n");
        sb.append("    phoneticSpelling: ").append(toIndentedString(phoneticSpelling)).append("\n");
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


