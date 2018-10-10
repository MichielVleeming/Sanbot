package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.LED;
import com.sanbot.opensdk.function.unit.HardWareManager;

public class LightsActivity extends MainActivity {
    HardWareManager hardWareManager;
    int i;

    @Override
    protected void onMainServiceConnected() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(LightsActivity.class);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        onMainServiceConnected();
        setContentView(R.layout.light_activity);
        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        i = 1;
        hardWareManager.setWhiteLightLevel(i);
    }


    public void onLightClick(View v) {
        int x = v.getId();
        switch (x) {
            case R.id.whiteLightOn:
                hardWareManager.switchWhiteLight(true);
                break;
            case R.id.whiteLightBrighter:
                hardWareManager.setWhiteLightLevel(i);
                if (i == 3) {
                    i = 0;
                }
                i++;
                break;
            case R.id.turnOnAllLed:
                hardWareManager.setLED(new LED(LED.PART_ALL, LED.MODE_YELLOW));
                break;
            case R.id.turnOnHead:
                hardWareManager.setLED(new LED(LED.PART_LEFT_HEAD, LED.MODE_BLUE));
                hardWareManager.setLED(new LED(LED.PART_RIGHT_HEAD, LED.MODE_RED));
                break;
            case R.id.turnLedsOff:
                hardWareManager.switchWhiteLight(false);
                hardWareManager.setLED(new LED(LED.PART_ALL, LED.MODE_CLOSE));
                break;
        }
    }
}

