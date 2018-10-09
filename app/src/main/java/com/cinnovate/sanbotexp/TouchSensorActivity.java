package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.interfaces.hardware.TouchSensorListener;

public class TouchSensorActivity extends MainActivity implements View.OnClickListener {
    HardWareManager hardWareManager;
    TextView touchListener;

    @Override
    protected void onMainServiceConnected() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(TouchSensorActivity.class);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        onMainServiceConnected();
        setContentView(R.layout.touch_sensor_activity);
        touchListener = findViewById(R.id.touchListener);
        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        hardWareManager.setOnHareWareListener(new TouchSensorListener() {
            @Override
            public void onTouch(int i) {
                if (i == 1) {
                    touchListener.setText("Right Chin was touched");
                }
                if (i == 2) {
                    touchListener.setText("Left Chin was touched");
                }
                if (i == 3) {
                    touchListener.setText("Left Chest was touched");
                }
                if (i == 4) {
                    touchListener.setText("Right Chest was touched");
                }
                if (i == 5) {
                    touchListener.setText("Left side of back of head was touched");
                }
                if (i == 6) {
                    touchListener.setText("Right side of back of head was touched");
                }
                if (i == 7) {
                    touchListener.setText("Left side of back was touched");
                }
                if (i == 8) {
                    touchListener.setText("Right side of back was touched");
                }
                if (i == 9) {
                    touchListener.setText("Left wing was touched");
                }
                if (i == 10) {
                    touchListener.setText("Right wing was touched");
                }
                if (i == 11) {
                    touchListener.setText("Center of head was touched");
                }
                if (i == 12) {
                    touchListener.setText("Right side of head was touched");
                }
                if (i == 13) {
                    touchListener.setText("Left side of head was touched");
                }
            }
        });
    }

}

