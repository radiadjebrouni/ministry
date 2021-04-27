package com.example.ministery;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;


import androidx.appcompat.app.AppCompatActivity;

import com.example.ministery.R;

import java.util.Locale;

public class TexttoSpeech extends AppCompatActivity {
    private android.speech.tts.TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView( R.layout.tts);
        mButtonSpeak = findViewById(R.id.button_speak);



        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   speak();
            }
        });
    }
    public void speak(String s, Context c) {

        mTTS = new android.speech.tts.TextToSpeech (c, new android.speech.tts.TextToSpeech.OnInitListener () {
            @Override
            public void onInit(int status) {
                if (status == android.speech.tts.TextToSpeech.SUCCESS) {

                    int result = mTTS.setLanguage( Locale.FRANCE);
                    if (result == android.speech.tts.TextToSpeech.LANG_MISSING_DATA
                            || result == android.speech.tts.TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } /*else {
                        mButtonSpeak.setEnabled(true);
                    }*/
                    float pitch = 1.0f;
                    float speed = 1.0f;
                    mTTS.setPitch(pitch);
                    mTTS.setSpeechRate(speed);
                    mTTS.speak(s, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
       // String text = mEditText.getText().toString();
        /*float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;*/



    }
    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }
}
