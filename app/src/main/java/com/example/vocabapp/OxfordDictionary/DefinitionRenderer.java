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
import androidx.transition.Transition;

import com.example.vocabapp.R;
import com.pedrogomez.renderers.Renderer;

public class DefinitionRenderer extends Renderer<Definition> {
    private TextView category;
    private TextView word;
    private TextView etymology;
    private TextView meaning;
    private TextView example;
    private TextView pronunciation;
    private CardView cardView;

    @Override
    protected void setUpView(View rootView) {
        category = (TextView) rootView.findViewById(R.id.category);
        word = (TextView) rootView.findViewById(R.id.word);
        etymology = (TextView) rootView.findViewById(R.id.etymology);
        meaning = (TextView) rootView.findViewById(R.id.defination);
        example = rootView.findViewById(R.id.example);
        pronunciation = rootView.findViewById(R.id.pronunciation);
        cardView = rootView.findViewById(R.id.card_view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void hookListeners(View rootView) {
        rootView.setOnClickListener(v -> {
            if (example.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(cardView, new AutoTransition());
                setVisible(word, true);
                setVisible(example, true);
                setVisible(etymology, true);
                setVisible(pronunciation, true);
                setVisible(category,true);
            } else if (example.getVisibility() == View.VISIBLE) {
                setVisible(example, false);
                setVisible(etymology, false);
                setVisible(pronunciation, false);
                setVisible(word, false);
                setVisible(category,false);
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
        setFieldText(word, definition.getWord());
        setFieldText(example, definition.getExample());
        setFieldText(meaning, definition.getDefinition());
        setFieldText(etymology, definition.getEtymology());
        setFieldText(pronunciation, definition.getPronunciation());
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
