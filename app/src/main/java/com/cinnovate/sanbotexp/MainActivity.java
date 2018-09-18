package com.cinnovate.sanbotexp;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sanbot.opensdk.base.TopBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.EmotionsType;
import com.sanbot.opensdk.function.beans.LED;
import com.sanbot.opensdk.function.beans.SpeakOption;
import com.sanbot.opensdk.function.beans.handmotion.NoAngleHandMotion;
import com.sanbot.opensdk.function.beans.handmotion.RelativeAngleHandMotion;
import com.sanbot.opensdk.function.beans.headmotion.AbsoluteAngleHeadMotion;
import com.sanbot.opensdk.function.beans.headmotion.RelativeAngleHeadMotion;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.beans.speech.SpeakStatus;
import com.sanbot.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.sanbot.opensdk.function.beans.wheelmotion.NoAngleWheelMotion;
import com.sanbot.opensdk.function.unit.HandMotionManager;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.HeadMotionManager;
import com.sanbot.opensdk.function.unit.ModularMotionManager;
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

public class MainActivity extends TopBaseActivity {

    HardWareManager hardWareManager;
    SpeechManager speechManager;
    HeadMotionManager headMotionManager;
    RelativeAngleHeadMotion relativeAngleHeadMotion;
    WheelMotionManager wheelMotionManager;
    SystemManager systemManager;
    EmotionsType currentEmotion, emotions[];
    SpeakOption speakOption = new SpeakOption();
    Button ledOn, ledOff, headLeft, headRight,
            headUp, headDown, buttonSayHi, buttonWheelForward,
            setEmotion;


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
        speakOption.setSpeed(30);
        ledOn = findViewById(R.id.ledOn);
        ledOff = findViewById(R.id.ledOff);
        headLeft = findViewById(R.id.headLeft);
        headRight = findViewById(R.id.headRight);
        headUp = findViewById(R.id.headUp);
        headDown = findViewById(R.id.headDown);
        buttonSayHi = findViewById(R.id.buttonSayHi);
        buttonWheelForward = findViewById(R.id.buttonWheelForward);
        setEmotion = findViewById(R.id.setEmotion);
        emotions = new EmotionsType[]{EmotionsType.ANGRY, EmotionsType.CRY, EmotionsType.SURPRISE, EmotionsType.KISS, EmotionsType.LAUGHTER};
        setonClicks();
        touchTest();
    }

    public void setonClicks() {
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
        setEmotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentEmotion();
            }
        });

    }

    public void currentEmotion() {
        Random rand = new Random();
        currentEmotion = emotions[rand.nextInt(emotions.length)];
        systemManager.showEmotion(currentEmotion);
    }

    public void wheelGoForward() {
        DistanceWheelMotion distanceWheelMotion = new DistanceWheelMotion(DistanceWheelMotion.ACTION_FORWARD_RUN, 5, 50);
        wheelMotionManager.doDistanceMotion(distanceWheelMotion);
    }

    public void setWhiteLightOn(View view) {
        hardWareManager.switchWhiteLight(true);
        hardWareManager.setLED(new LED(LED.PART_ALL, LED.MODE_FLICKER_RANDOM, (byte) 10, (byte) 3));
        Snackbar.make(view, R.string.white_light_on, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    public void testSpeech() {
        speechManager.startSpeak("Hello there, my name is San Bot", speakOption);
    }

    public void setLightsOff(View view) {
        hardWareManager.setLED(new LED(LED.PART_ALL, LED.MODE_CLOSE, (byte) 10, (byte) 3));
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
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_UP, 30);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            case "down":
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_DOWN, 30);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
        }
    }


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
