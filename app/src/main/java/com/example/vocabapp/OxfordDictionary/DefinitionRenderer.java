package com.example.vocabapp.OxfordDictionary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vocabapp.R;
import com.pedrogomez.renderers.Renderer;

public class DefinitionRenderer extends Renderer<Definition> {
    private TextView category;
    private TextView word;
    private TextView etymology;
    private TextView meaning;
    private TextView example;

    @Override
    protected void setUpView(View rootView) {
        category = (TextView) rootView.findViewById(R.id.category);
        word = (TextView) rootView.findViewById(R.id.word);
       // etymology = (TextView) rootView.findViewById(R.id.etymology);
        meaning = (TextView) rootView.findViewById(R.id.defination);
        example = rootView.findViewById(R.id.example);
    }

    @Override
    protected void hookListeners(View rootView) {
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
    }

    private void setFieldText(TextView field, String text) {
        field.setText(text);
        field.setVisibility(text != null ? View.VISIBLE : View.GONE);
    }
}
