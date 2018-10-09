package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.EmotionsType;
import com.sanbot.opensdk.function.unit.SystemManager;

public class EmotionActivity extends MainActivity {
    SystemManager systemManager;

    @Override
    protected void onMainServiceConnected() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(EmotionActivity.class);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        onMainServiceConnected();
        setContentView(R.layout.emotion_activity);
        systemManager = (SystemManager) getUnitManager(FuncConstant.SYSTEM_MANAGER);
    }

    public void onEmotionClick(View v) {
        switch (v.getId()) {
            case R.id.madEmotionButton:
                systemManager.showEmotion(EmotionsType.ANGRY);
                break;
            case R.id.shyEmotionButton:
                systemManager.showEmotion(EmotionsType.SHY);
                break;
            case R.id.smileEmotionButton:
                systemManager.showEmotion(EmotionsType.SMILE);
        }
    }

}
