package com.cinnovate.sanbotexp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.sanbot.opensdk.base.TopBaseActivity;
import com.sanbot.opensdk.function.beans.SpeakOption;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends TopBaseActivity {
    SpeakOption speakOption = new SpeakOption();
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
        setBodyView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("all");


    }

    public void onMainClick(View v) {
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
            case R.id.FaceRecognitionStarter:
                i = new Intent(this, FaceRecognitionActivity.class);
                break;
        }
        startActivity(i);
    }
}
