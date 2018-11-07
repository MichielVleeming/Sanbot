package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.wheelmotion.RelativeAngleWheelMotion;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.WheelMotionManager;
import com.sanbot.opensdk.function.unit.interfaces.hardware.VoiceLocateListener;

public class SoundSourceActivity extends MainActivity {
    HardWareManager hardWareManager;
    public WheelMotionManager wheelMotionManager;
    RelativeAngleWheelMotion relativeAngleWheelMotion;
    TextView textView;

    @Override
    protected void onMainServiceConnected() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(SoundSourceActivity.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        onMainServiceConnected();
        setContentView(R.layout.activity_sound_source);
        textView = findViewById(R.id.soundSource);
        wheelMotionManager = (WheelMotionManager) getUnitManager(FuncConstant.WHEELMOTION_MANAGER);

        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        hardWareManager.setOnHareWareListener(new VoiceLocateListener() {
            @Override
            public void voiceLocateResult(int soundAngle) {

                String x = Integer.toString(soundAngle);
                textView.setText(x);
                turntoSound(soundAngle);

            }

            private void turntoSound(int soundAngle) {
                if (soundAngle < 180) {
                    relativeAngleWheelMotion = new RelativeAngleWheelMotion(RelativeAngleWheelMotion.TURN_RIGHT, 5, soundAngle);


                }
                if (soundAngle > 180 && soundAngle < 360) {
                    relativeAngleWheelMotion = new RelativeAngleWheelMotion(RelativeAngleWheelMotion.TURN_LEFT, 5, 360-soundAngle);
                }
                wheelMotionManager.doRelativeAngleMotion(relativeAngleWheelMotion);
            }

        });
    }
}
