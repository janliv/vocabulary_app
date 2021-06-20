package com.example.vocabapp.OxfordDictionary;

import android.media.MediaPlayer;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
    private ImageButton urlAudio;
    private CardView cardView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void setUpView(View rootView) {
        category = rootView.findViewById(R.id.category);
        word = rootView.findViewById(R.id.word);
        etymology = rootView.findViewById(R.id.etymology);
        meaning = rootView.findViewById(R.id.defination);
        example = rootView.findViewById(R.id.example);
        pronunciation = rootView.findViewById(R.id.pronunciation);
        cardView = rootView.findViewById(R.id.card_view);
        antonyms = rootView.findViewById(R.id.antonyms);
        synonyms = rootView.findViewById(R.id.synonyms);
//        urlAudio = rootView.findViewById(R.id.pronounce_image_button);
//        mediaPlayer = new MediaPlayer();
//        mediaPlayer.setOnPreparedListener(prepare -> {
//            urlAudio.setVisibility(View.VISIBLE);
//            urlAudio.setOnClickListener(v -> mediaPlayer.start());
//        });
//        mediaPlayer.setAudioStreamType(AudioManager.USE_DEFAULT_STREAM_TYPE);
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
                setVisible(category, true);
                setVisible(antonyms, true);
                setVisible(synonyms, true);

            } else if (word.getVisibility() == View.VISIBLE) {
                setVisible(example, false);
                setVisible(etymology, false);
                setVisible(pronunciation, false);
                setVisible(word, false);
                setVisible(category, false);
                setVisible(synonyms, false);
                setVisible(antonyms, false);
//                urlAudio.setVisibility(View.GONE);
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                    mediaPlayer.prepareAsync();
//                }
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
        setVisible(category, false);
        setFieldText(word, definition.getWord());
        setVisible(word, false);
        setFieldText(example, definition.getExample());
        setVisible(example, false);
        setFieldText(meaning, definition.getDefinition());
        setFieldText(etymology, definition.getEtymology());
        setVisible(etymology, false);
        setFieldText(pronunciation, definition.getPronunciation());
        setVisible(pronunciation, false);
        setFieldText(antonyms, definition.getAntonyms());
        setVisible(antonyms, false);
        setFieldText(synonyms, definition.getSynonyms());
        setVisible(synonyms, false);
//        urlAudio.setVisibility(View.GONE);
//
//        try {
//            mediaPlayer.setDataSource(definition.getPronunciationUrl());
//            mediaPlayer.prepare();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
