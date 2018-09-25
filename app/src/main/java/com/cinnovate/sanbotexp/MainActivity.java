package com.cinnovate.sanbotexp;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends TopBaseActivity {

    HardWareManager hardWareManager;
    SpeechManager speechManager;
    HeadMotionManager headMotionManager;
    RelativeAngleHeadMotion relativeAngleHeadMotion;
    AbsoluteAngleHeadMotion absoluteAngleHeadMotion;
    LocateAbsoluteAngleHeadMotion locateAbsoluteAngleHeadMotion;
    ProjectorManager projectorManager;
    WheelMotionManager wheelMotionManager;
    SystemManager systemManager;
    EmotionsType currentEmotion, emotions[];
    SpeakOption speakOption = new SpeakOption();
    Button ledOn, ledOff, headLeft, headRight,
            headUp, headDown, buttonSayHi, buttonWheelForward,
            setEmotion, headAbsoluteLeft, headAbsoluteRight, headCenter,
            headAbsoluteUp, headAbsoluteDown, moveForward,
            videoStream, closeStream;


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
        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
        headMotionManager = (HeadMotionManager) getUnitManager(FuncConstant.HEADMOTION_MANAGER);
        wheelMotionManager = (WheelMotionManager) getUnitManager(FuncConstant.WHEELMOTION_MANAGER);
        systemManager = (SystemManager) getUnitManager(FuncConstant.SYSTEM_MANAGER);
        projectorManager = (ProjectorManager) getUnitManager(FuncConstant.PROJECTOR_MANAGER);
        speakOption.setSpeed(30);
        ledOn = findViewById(R.id.ledOn);
        videoStream = findViewById(R.id.videoStream);
        ledOff = findViewById(R.id.ledOff);
        headLeft = findViewById(R.id.headLeft);
        headRight = findViewById(R.id.headRight);
        closeStream = findViewById(R.id.closeStream);
        headUp = findViewById(R.id.headUp);
        headDown = findViewById(R.id.headDown);
        headAbsoluteLeft = findViewById(R.id.headAbsoluteLeft);
        headAbsoluteRight = findViewById(R.id.headAbsoluteRight);
        headAbsoluteUp = findViewById(R.id.headAbsoluteUp);
        headAbsoluteDown = findViewById(R.id.headAbsoluteDown);
        buttonSayHi = findViewById(R.id.buttonSayHi);
        buttonWheelForward = findViewById(R.id.buttonWheelForward);
        setEmotion = findViewById(R.id.setEmotion);
        headCenter = findViewById(R.id.headCenter);
        emotions = new EmotionsType[]{EmotionsType.CRY, EmotionsType.SURPRISE, EmotionsType.KISS, EmotionsType.LAUGHTER};
        moveForward = findViewById(R.id.moveForward);
        setonClicks();
        touchTest();
    }

    public void setonClicks() {
        videoStream.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startVideoStream();
            }
        });
//        closeStream.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                closeStream();
//            }
//        });
        ledOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWhiteLightOn(view);

            }
        });
        ledOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLightsOff(view);
            }
        });
        headLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnHead("left");
            }
        });
        headRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnHead("right");
            }
        });
        headUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnHead("up");
            }
        });
        headDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnHead("down");
            }
        });
        headCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnHead("center");
            }
        });
        headAbsoluteLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnHead("absoluteLeft");
            }
        });
        headAbsoluteRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnHead("absoluteRight");
            }
        });
        headAbsoluteUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnHead("absoluteUp");
            }
        });
        headAbsoluteDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnHead("absoluteDown");
            }
        });
        buttonSayHi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testSpeech();
            }
        });
        buttonWheelForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wheelGoForward();
            }
        });
        moveForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveWheel("forwardOneMeter");
            }
        });



    }

    public void currentEmotion() {
        Random rand = new Random();
        currentEmotion = EmotionsType.ANGRY;
        systemManager.showEmotion(currentEmotion);
    }

    public void wheelGoForward() {
        DistanceWheelMotion distanceWheelMotion = new DistanceWheelMotion(DistanceWheelMotion.ACTION_FORWARD_RUN, 5, 50);
        wheelMotionManager.doDistanceMotion(distanceWheelMotion);
    }

    public void setWhiteLightOn(View view) {
        hardWareManager.setWhiteLightLevel(3);
        hardWareManager.switchWhiteLight(true);
        hardWareManager.setLED(new LED(LED.PART_ALL, LED.MODE_PINK));

        Snackbar.make(view, R.string.white_light_on, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    public void testSpeech() {
        speechManager.startSpeak("Hello there, my name is San Bot", speakOption);
    }

    public void setLightsOff(View view) {
        hardWareManager.setLED(new LED(LED.PART_ALL, LED.MODE_CLOSE));
        hardWareManager.switchWhiteLight(false);
    }

    public void touchTest() {
        hardWareManager.setOnHareWareListener(new TouchSensorListener() {
            @Override
            public void onTouch(int part) {
                if (part == 11 || part == 12 || part == 13 || part == 10 || part == 9) {
                    hardWareManager.switchWhiteLight(true);

                }
            }
        });
    }

    public void turnHead(String headMovement) {
        switch (headMovement) {
            case "right":
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_RIGHT, 30);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            case "left":
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_LEFT, 30);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            case "up":
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_LEFTUP, 80);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            case "down":
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_DOWN, 30);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            case "absoluteRight":
                absoluteAngleHeadMotion = new AbsoluteAngleHeadMotion(AbsoluteAngleHeadMotion.ACTION_HORIZONTAL, 180);
                headMotionManager.doAbsoluteAngleMotion(absoluteAngleHeadMotion);
                break;
            case "absoluteLeft":
                absoluteAngleHeadMotion = new AbsoluteAngleHeadMotion(AbsoluteAngleHeadMotion.ACTION_HORIZONTAL, 0);
                headMotionManager.doAbsoluteAngleMotion(absoluteAngleHeadMotion);
                break;
            case "absoluteUp":
                absoluteAngleHeadMotion = new AbsoluteAngleHeadMotion(AbsoluteAngleHeadMotion.ACTION_VERTICAL, 30);
                headMotionManager.doAbsoluteAngleMotion(absoluteAngleHeadMotion);
                break;
            case "absoluteDown":
                absoluteAngleHeadMotion = new AbsoluteAngleHeadMotion(AbsoluteAngleHeadMotion.ACTION_VERTICAL, 7);
                headMotionManager.doAbsoluteAngleMotion(absoluteAngleHeadMotion);
                break;
            case "center":
                locateAbsoluteAngleHeadMotion = new LocateAbsoluteAngleHeadMotion(LocateAbsoluteAngleHeadMotion.ACTION_BOTH_LOCK, 90, 15);
                headMotionManager.doAbsoluteLocateMotion(locateAbsoluteAngleHeadMotion);
                break;
        }

    }

    public void moveWheel(String moveSanbot) {
        switch (moveSanbot) {
            case "forwardOneMeter":
                RelativeAngleWheelMotion distanceWheelMotion = new RelativeAngleWheelMotion(RelativeAngleWheelMotion.TURN_LEFT, 5, 50);
                wheelMotionManager.doRelativeAngleMotion(distanceWheelMotion);
                break;
        }
    }
    public void startVideoStream(){
        projectorManager.switchProjector(true);
        projectorManager.setMode(ProjectorManager.MODE_WALL);

    }
//    public void closeStream(){
//        mediaManager.closeStream();
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
