package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.interfaces.hardware.InfrareListener;
import com.sanbot.opensdk.function.unit.interfaces.hardware.PIRListener;

public class PirActivity extends MainActivity {
    TextView pirValue;
    HardWareManager hardWareManager;

    @Override
    protected void onMainServiceConnected() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(PirActivity.class);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        onMainServiceConnected();
        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        setContentView(R.layout.activity_pir);
        pirValue = findViewById(R.id.pir_value);
        setPirListener();
        setPirDistanceListener();
    }

    private void setPirListener() {
        hardWareManager.setOnHareWareListener(new PIRListener() {
            @Override
            public void onPIRCheckResult(boolean b, int part) {
                pirValue.setText((part == 1 ? getResources().getString(R.string.pir_front) : getResources().getString(R.string.pir_back)));
            }
        });
    }

    private void setPirDistanceListener() {
        hardWareManager.setOnHareWareListener(new InfrareListener() {
            @Override
            public void infrareDistance(int part, int distance) {
                Log.d("DistanceMeasure", "The part is: " + part + "Distance is: " + distance);
            }
        });
    }

}
