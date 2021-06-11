// Generated by view binder compiler. Do not edit!
package com.example.vocabapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.example.vocabapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class DefinitionRowBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView antonyms;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final TextView category;

  @NonNull
  public final TextView defination;

  @NonNull
  public final TextView etymology;

  @NonNull
  public final TextView example;

  @NonNull
  public final TextView pronunciation;

  @NonNull
  public final TextView synonyms;

  @NonNull
  public final TextView word;

  private DefinitionRowBinding(@NonNull CardView rootView, @NonNull TextView antonyms,
      @NonNull CardView cardView, @NonNull TextView category, @NonNull TextView defination,
      @NonNull TextView etymology, @NonNull TextView example, @NonNull TextView pronunciation,
      @NonNull TextView synonyms, @NonNull TextView word) {
    this.rootView = rootView;
    this.antonyms = antonyms;
    this.cardView = cardView;
    this.category = category;
    this.defination = defination;
    this.etymology = etymology;
    this.example = example;
    this.pronunciation = pronunciation;
    this.synonyms = synonyms;
    this.word = word;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static DefinitionRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static DefinitionRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.definition_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static DefinitionRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.antonyms;
      TextView antonyms = rootView.findViewById(id);
      if (antonyms == null) {
        break missingId;
      }

      CardView cardView = (CardView) rootView;

      id = R.id.category;
      TextView category = rootView.findViewById(id);
      if (category == null) {
        break missingId;
      }

      id = R.id.defination;
      TextView defination = rootView.findViewById(id);
      if (defination == null) {
        break missingId;
      }

      id = R.id.etymology;
      TextView etymology = rootView.findViewById(id);
      if (etymology == null) {
        break missingId;
      }

      id = R.id.example;
      TextView example = rootView.findViewById(id);
      if (example == null) {
        break missingId;
      }

      id = R.id.pronunciation;
      TextView pronunciation = rootView.findViewById(id);
      if (pronunciation == null) {
        break missingId;
      }

      id = R.id.synonyms;
      TextView synonyms = rootView.findViewById(id);
      if (synonyms == null) {
        break missingId;
      }

      id = R.id.word;
      TextView word = rootView.findViewById(id);
      if (word == null) {
        break missingId;
      }

      return new DefinitionRowBinding((CardView) rootView, antonyms, cardView, category, defination,
          etymology, example, pronunciation, synonyms, word);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
