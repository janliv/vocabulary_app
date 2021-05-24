package com.example.vocabapp.OxfordDictionary;

import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.example.vocabapp.R;
import com.pedrogomez.renderers.Renderer;

public class DefinitionRenderer extends Renderer<Definition> {
    private TextView category;
    private TextView word;
    private TextView etymology;
    private TextView meaning;
    private TextView example;
    private TextView pronunciation;
    private TextView antonyms;
    private TextView synonyms;
    private CardView cardView;

    @Override
    protected void setUpView(View rootView) {
        category =  rootView.findViewById(R.id.category);
        word = rootView.findViewById(R.id.word);
        etymology = rootView.findViewById(R.id.etymology);
        meaning = rootView.findViewById(R.id.defination);
        example = rootView.findViewById(R.id.example);
        pronunciation = rootView.findViewById(R.id.pronunciation);
        cardView = rootView.findViewById(R.id.card_view);
        antonyms = rootView.findViewById(R.id.antonyms);
        synonyms = rootView.findViewById(R.id.synonyms);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(v -> {
            if (word.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                setVisible(word, true);
                setVisible(example, true);
                setVisible(etymology, true);
                setVisible(pronunciation, true);
                setVisible(category,true);
                setVisible(antonyms,true);
                setVisible(synonyms,true);
            } else if (word.getVisibility() == View.VISIBLE) {
                setVisible(example, false);
                setVisible(etymology, false);
                setVisible(pronunciation, false);
                setVisible(word, false);
                setVisible(category,false);
                setVisible(synonyms,false);
                setVisible(antonyms,false);
                //TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
            }
        });
    }

    @Override
    protected View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.definition_row, parent, false);
    }

    @Override
    public void render() {
        Definition definition = getContent();
        setFieldText(category, definition.getCategory());
        setVisible(category,false);
        setFieldText(word, definition.getWord());
        setVisible(word, false);
        setFieldText(example, definition.getExample());
        setVisible(example, false);
        setFieldText(meaning, definition.getDefinition());
        setFieldText(etymology, definition.getEtymology());
        setVisible(etymology, false);
        setFieldText(pronunciation, definition.getPronunciation());
        setVisible(pronunciation, false);
        setFieldText(antonyms,definition.getAntonyms());
        setVisible(antonyms,false);
        setFieldText(synonyms,definition.getSynonyms());
        setVisible(synonyms,false);
    }

    private void setFieldText(TextView field, String text) {
        field.setText(text);
        //field.setVisibility(text != null ? View.VISIBLE : View.GONE);
        if (text == null)
            field.setVisibility(View.GONE);
    }

    private void setVisible(TextView field, boolean isVisible) {
        if (isVisible && field.getText() != "")
            field.setVisibility(View.VISIBLE);
        if (!isVisible)
            field.setVisibility(View.GONE);
    }
}
