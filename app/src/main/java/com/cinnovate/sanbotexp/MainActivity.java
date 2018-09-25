package com.cinnovate.sanbotexp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.method.Touch;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.qihancloud.opensdk.function.unit.MediaManager;
import com.sanbot.opensdk.base.TopBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.beans.OperationResult;
import com.sanbot.opensdk.function.beans.EmotionsType;
import com.sanbot.opensdk.function.beans.LED;
import com.sanbot.opensdk.function.beans.SpeakOption;
import com.sanbot.opensdk.function.beans.StreamOption;
import com.sanbot.opensdk.function.beans.handmotion.NoAngleHandMotion;
import com.sanbot.opensdk.function.beans.handmotion.RelativeAngleHandMotion;
import com.sanbot.opensdk.function.beans.headmotion.AbsoluteAngleHeadMotion;
import com.sanbot.opensdk.function.beans.headmotion.LocateAbsoluteAngleHeadMotion;
import com.sanbot.opensdk.function.beans.headmotion.LocateRelativeAngleHeadMotion;
import com.sanbot.opensdk.function.beans.headmotion.RelativeAngleHeadMotion;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.beans.speech.SpeakStatus;
import com.sanbot.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.sanbot.opensdk.function.beans.wheelmotion.NoAngleWheelMotion;
import com.sanbot.opensdk.function.beans.wheelmotion.RelativeAngleWheelMotion;
import com.sanbot.opensdk.function.unit.HandMotionManager;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.HeadMotionManager;
import com.sanbot.opensdk.function.unit.ModularMotionManager;
import com.sanbot.opensdk.function.unit.ProjectorManager;
import com.sanbot.opensdk.function.unit.SpeechManager;
import com.sanbot.opensdk.function.unit.SystemManager;
import com.sanbot.opensdk.function.unit.WheelMotionManager;
import com.sanbot.opensdk.function.unit.interfaces.hardware.HardWareListener;
import com.sanbot.opensdk.function.unit.interfaces.hardware.InfrareListener;
import com.sanbot.opensdk.function.unit.interfaces.hardware.PIRListener;
import com.sanbot.opensdk.function.unit.interfaces.hardware.TouchSensorListener;
import com.sanbot.opensdk.function.unit.interfaces.hardware.VoiceLocateListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.RecognizeListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.SpeakListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.stream.Stream;

public class MainActivity extends TopBaseActivity implements View.OnClickListener {
    SpeakOption speakOption = new SpeakOption();
    Button lightActivityStarter, wheelActivityStarter,
            headMovementActivityStarter, touchSensorActivityStarter,
            speechActivityStarter, armActivityStarter;
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

        wheelActivityStarter.setOnClickListener(this);
        lightActivityStarter.setOnClickListener(this);
        headMovementActivityStarter.setOnClickListener(this);
        touchSensorActivityStarter.setOnClickListener(this);
        speechActivityStarter.setOnClickListener(this);
        armActivityStarter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int x = v.getId();
        switch (x) {
            case R.id.wheelActivityStarter:
                i = new Intent(this, WheelsActivity.class);
                startActivity(i);
                break;
            case R.id.lightsActivityStarter:
                i = new Intent(this, LightsActivity.class);
                startActivity(i);
                break;
            case R.id.headMovementActivityStarter:
                i = new Intent(this, HeadMovementActivity.class);
                startActivity(i);
                break;
            case R.id.touchSensorActivityStarter:
                i = new Intent(this, TouchSensorActivity.class);
                startActivity(i);
                break;
            case R.id.speechActivityStarter:
                i = new Intent(this, SpeechActivity.class);
                startActivity(i);
                break;
            case R.id.armMovementActivityStarter:
                i = new Intent(this, ArmMovementActivity.class);
                startActivity(i);
                break;
        }
    }
}
