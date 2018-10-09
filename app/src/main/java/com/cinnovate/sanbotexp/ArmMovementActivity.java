package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.handmotion.AbsoluteAngleHandMotion;
import com.sanbot.opensdk.function.beans.handmotion.NoAngleHandMotion;
import com.sanbot.opensdk.function.unit.HandMotionManager;

public class ArmMovementActivity extends MainActivity implements View.OnClickListener {
    HandMotionManager handMotionManager;
    Button moveHandsUp, moveHandsDown;

    @Override
    protected void onMainServiceConnected() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(ArmMovementActivity.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        onMainServiceConnected();
        setContentView(R.layout.activity_arm_movement);
        handMotionManager = (HandMotionManager) getUnitManager(FuncConstant.HANDMOTION_MANAGER);
        moveHandsUp = findViewById(R.id.moveHandsUp);
        moveHandsUp.setOnClickListener(this);
        moveHandsDown = findViewById(R.id.moveHandsDown);
        moveHandsDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moveHandsUp:
                NoAngleHandMotion noAngleHandMotion = new NoAngleHandMotion(NoAngleHandMotion.PART_BOTH,
                        5, NoAngleHandMotion.ACTION_UP);
                handMotionManager.doNoAngleMotion(noAngleHandMotion);
                break;
            case R.id.moveHandsDown:
                AbsoluteAngleHandMotion absoluteAngleHandMotion = new AbsoluteAngleHandMotion(AbsoluteAngleHandMotion.PART_BOTH,
                        5, 30);
                handMotionManager.doAbsoluteAngleMotion(absoluteAngleHandMotion);
                break;
        }
    }
}
