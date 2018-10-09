package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.interfaces.hardware.VoiceLocateListener;

public class SoundSourceActivity extends MainActivity {
    HardWareManager hardWareManager;
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

        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        hardWareManager.setOnHareWareListener(new VoiceLocateListener() {
            @Override
            public void voiceLocateResult(int i) {
                String x = Integer.toString(i);
                textView.setText(x);
            }
        });
    }
}
