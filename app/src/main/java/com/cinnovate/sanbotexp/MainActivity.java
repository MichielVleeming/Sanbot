package com.cinnovate.sanbotexp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.sanbot.opensdk.base.TopBaseActivity;
import com.sanbot.opensdk.function.beans.SpeakOption;

public class MainActivity extends TopBaseActivity implements View.OnClickListener {
    SpeakOption speakOption = new SpeakOption();
    Button lightActivityStarter, wheelActivityStarter,
            headMovementActivityStarter, touchSensorActivityStarter,
            speechActivityStarter, armActivityStarter, soundSourceStarter,
            cameraStarter, pirStarter, projectorStarter, emotionStarter;
    Intent i;

    @Override
    protected void onMainServiceConnected() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(MainActivity.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        onMainServiceConnected();
        setContentView(R.layout.activity_main);
        wheelActivityStarter = findViewById(R.id.wheelActivityStarter);
        lightActivityStarter = findViewById(R.id.lightsActivityStarter);
        headMovementActivityStarter = findViewById(R.id.headMovementActivityStarter);
        touchSensorActivityStarter = findViewById(R.id.touchSensorActivityStarter);
        speechActivityStarter = findViewById(R.id.speechActivityStarter);
        armActivityStarter = findViewById(R.id.armMovementActivityStarter);
        soundSourceStarter = findViewById(R.id.soundSourceStarter);
        cameraStarter = findViewById(R.id.cameraStarter);
        pirStarter = findViewById(R.id.pirStarter);
        projectorStarter = findViewById(R.id.projectorStarter);
        emotionStarter = findViewById(R.id.emotionStarter);

        wheelActivityStarter.setOnClickListener(this);
        lightActivityStarter.setOnClickListener(this);
        headMovementActivityStarter.setOnClickListener(this);
        touchSensorActivityStarter.setOnClickListener(this);
        speechActivityStarter.setOnClickListener(this);
        armActivityStarter.setOnClickListener(this);
        soundSourceStarter.setOnClickListener(this);
        cameraStarter.setOnClickListener(this);
        pirStarter.setOnClickListener(this);
        projectorStarter.setOnClickListener(this);
        emotionStarter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.wheelActivityStarter:
                i = new Intent(this, WheelsActivity.class);
                break;
            case R.id.lightsActivityStarter:
                i = new Intent(this, LightsActivity.class);
                break;
            case R.id.headMovementActivityStarter:
                i = new Intent(this, HeadMovementActivity.class);
                break;
            case R.id.touchSensorActivityStarter:
                i = new Intent(this, TouchSensorActivity.class);
                break;
            case R.id.speechActivityStarter:
                i = new Intent(this, SpeechActivity.class);
                break;
            case R.id.armMovementActivityStarter:
                i = new Intent(this, ArmMovementActivity.class);
                break;
            case R.id.soundSourceStarter:
                i = new Intent(this, SoundSourceActivity.class);
                break;
            case R.id.cameraStarter:
                i = new Intent(this, CameraActivity.class);
                break;
            case R.id.pirStarter:
                i = new Intent(this, PirActivity.class);
                break;
            case R.id.projectorStarter:
                i = new Intent(this, ProjectorActivity.class);
                break;
            case R.id.emotionStarter:
                i = new Intent(this, EmotionActivity.class);
                break;
        }
        startActivity(i);
    }
}
