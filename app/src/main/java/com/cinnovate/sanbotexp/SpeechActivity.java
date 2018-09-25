package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.SpeakOption;
import com.sanbot.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.sanbot.opensdk.function.unit.SpeechManager;
import com.sanbot.opensdk.function.unit.WheelMotionManager;

public class SpeechActivity extends MainActivity implements View.OnClickListener {
    SpeechManager speechManager;
    EditText testText;



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
        testText = findViewById(R.id.testText);
        SpeakOption speakOption = new SpeakOption();
        speakOption.setSpeed(5);
        startText.setOnClickListener(this);
        loremText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.startText) {
            speechManager.startSpeak(testText.getText().toString(), speakOption);
        }
        if (v.getId() == R.id.exampleText) {
            speechManager.startSpeak("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", speakOption);
        }
    }
}