package com.cinnovate.sanbotexp;

import android.media.MicrophoneInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.SpeakOption;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.unit.SpeechManager;
import com.sanbot.opensdk.function.unit.interfaces.speech.RecognizeListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.SpeechListener;

public class SpeechActivity extends MainActivity implements View.OnClickListener {
    SpeechManager speechManager;
    EditText testText;
    TextView spokenText, speakPercentage;

    @Override
    protected void onMainServiceConnected() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(WheelsActivity.class);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        onMainServiceConnected();
        setContentView(R.layout.speech_activity);
        speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
        Button startText = findViewById(R.id.startText);
        Button loremText = findViewById(R.id.exampleText);
        Button sleepSpeech = findViewById(R.id.sleepSpeech);
        Button awakeSpeech = findViewById(R.id.awakeSpeech);
        speakPercentage = findViewById(R.id.speakStatus);
        testText = findViewById(R.id.testText);
        spokenText = findViewById(R.id.spokenText);
        SpeakOption speakOption = new SpeakOption();
        speakOption.setSpeed(5);
        speakOption.setLanguageType(SpeakOption.LAG_ENGLISH_US);
        startText.setOnClickListener(this);
        loremText.setOnClickListener(this);
        sleepSpeech.setOnClickListener(this);
        awakeSpeech.setOnClickListener(this);


        setListener();
    }

    private void setListener() {

        speechManager.setOnSpeechListener(new RecognizeListener() {
            @Override
            public boolean onRecognizeResult(Grammar grammar) {
                Log.e("service: ", grammar.getText());
                spokenText.setText(grammar.getText());
                return true;
            }

            @Override
            public void onRecognizeVolume(int i) {

            }

            @Override
            public void onStartRecognize() {
                spokenText.setText("Recognizing speech");
            }

            @Override
            public void onStopRecognize() {

            }

            @Override
            public void onError(int i, int i1) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startText:
                speechManager.startSpeak(testText.getText().toString(), speakOption);
                break;
            case R.id.exampleText:
                speechManager.startSpeak(getResources().getString(R.string.lorem_ipsum), speakOption);
                break;
            case R.id.sleepSpeech:
                speechManager.pauseSpeak();
                break;
            case R.id.awakeSpeech:
                speechManager.resumeSpeak();
                break;


        }
    }

}