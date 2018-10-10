package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.interfaces.hardware.TouchSensorListener;

public class TouchSensorActivity extends MainActivity {
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
                    touchListener.setText(R.string.right_chin);
                }
                if (i == 2) {
                    touchListener.setText(R.string.left_chin);
                }
                if (i == 3) {
                    touchListener.setText(R.string.left_chest);
                }
                if (i == 4) {
                    touchListener.setText(R.string.right_chest);
                }
                if (i == 5) {
                    touchListener.setText(R.string.left_chest_back);
                }
                if (i == 6) {
                    touchListener.setText(R.string.right_chest_back);
                }
                if (i == 7) {
                    touchListener.setText(R.string.left_back);
                }
                if (i == 8) {
                    touchListener.setText(R.string.right_back);
                }
                if (i == 9) {
                    touchListener.setText(R.string.left_wing);
                }
                if (i == 10) {
                    touchListener.setText(R.string.right_wing);
                }
                if (i == 11) {
                    touchListener.setText(R.string.center_head);
                }
                if (i == 12) {
                    touchListener.setText(R.string.right_head);
                }
                if (i == 13) {
                    touchListener.setText(R.string.left_head);
                }
            }
        });
    }

}

