package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.headmotion.AbsoluteAngleHeadMotion;
import com.sanbot.opensdk.function.beans.headmotion.LocateAbsoluteAngleHeadMotion;
import com.sanbot.opensdk.function.beans.headmotion.RelativeAngleHeadMotion;
import com.sanbot.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.sanbot.opensdk.function.unit.HeadMotionManager;
import com.sanbot.opensdk.function.unit.WheelMotionManager;

public class HeadMovementActivity extends MainActivity implements View.OnClickListener {
    HeadMotionManager headMotionManager;
    Button headLeft, headRight, headUp, headDown, headReset,
    headAbsoluteLeft, headAbsoluteRight, headAbsoluteUp, headAbsoluteDown;

    @Override
    protected void onMainServiceConnected() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(WheelsActivity.class);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        onMainServiceConnected();
        setContentView(R.layout.head_movement_activity);
        headMotionManager = (HeadMotionManager) getUnitManager(FuncConstant.HEADMOTION_MANAGER);

        headLeft = findViewById(R.id.moveHeadLeft);
        headRight = findViewById(R.id.moveHeadRight);
        headUp = findViewById(R.id.moveHeadUp);
        headDown = findViewById(R.id.moveHeadDown);
        headReset = findViewById(R.id.resetHead);
        headAbsoluteLeft = findViewById(R.id.moveHeadAbsoluteLeft);
        headAbsoluteRight = findViewById(R.id.moveHeadAbsoluteRight);
        headAbsoluteUp = findViewById(R.id.moveHeadAbsoluteUp);
        headAbsoluteDown = findViewById(R.id.moveHeadAbsoluteDown);


        headLeft.setOnClickListener(this);
        headRight.setOnClickListener(this);
        headUp.setOnClickListener(this);
        headDown.setOnClickListener(this);
        headReset.setOnClickListener(this);
        headAbsoluteLeft.setOnClickListener(this);
        headAbsoluteRight.setOnClickListener(this);
        headAbsoluteUp.setOnClickListener(this);
        headAbsoluteDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        RelativeAngleHeadMotion relativeAngleHeadMotion;
        AbsoluteAngleHeadMotion absoluteAngleHeadMotion;
        switch (v.getId()){
            case R.id.moveHeadLeft:
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_LEFT, 30);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            case R.id.moveHeadRight:
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_RIGHT, 30);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            case R.id.moveHeadUp:
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_UP, 30);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            case R.id.moveHeadDown:
                relativeAngleHeadMotion = new RelativeAngleHeadMotion(RelativeAngleHeadMotion.ACTION_DOWN, 30);
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);
                break;
            case R.id.moveHeadAbsoluteLeft:
                absoluteAngleHeadMotion = new AbsoluteAngleHeadMotion(AbsoluteAngleHeadMotion.ACTION_HORIZONTAL,25);
                headMotionManager.doAbsoluteAngleMotion(absoluteAngleHeadMotion);
                break;
            case R.id.moveHeadAbsoluteRight:
                absoluteAngleHeadMotion = new AbsoluteAngleHeadMotion(AbsoluteAngleHeadMotion.ACTION_HORIZONTAL,160);
                headMotionManager.doAbsoluteAngleMotion(absoluteAngleHeadMotion);
                break;
            case R.id.moveHeadAbsoluteUp:
                absoluteAngleHeadMotion = new AbsoluteAngleHeadMotion(AbsoluteAngleHeadMotion.ACTION_VERTICAL,25);
                headMotionManager.doAbsoluteAngleMotion(absoluteAngleHeadMotion);
                break;
            case R.id.moveHeadAbsoluteDown:
                absoluteAngleHeadMotion = new AbsoluteAngleHeadMotion(AbsoluteAngleHeadMotion.ACTION_VERTICAL,10);
                headMotionManager.doAbsoluteAngleMotion(absoluteAngleHeadMotion);
                break;
            case R.id.resetHead:
                locateAbsoluteAngleHeadMotion = new LocateAbsoluteAngleHeadMotion(LocateAbsoluteAngleHeadMotion.ACTION_BOTH_LOCK, 90, 15);
                headMotionManager.doAbsoluteLocateMotion(locateAbsoluteAngleHeadMotion);
        }
    }
}