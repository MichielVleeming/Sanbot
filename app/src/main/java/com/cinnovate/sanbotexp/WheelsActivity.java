package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.handmotion.NoAngleHandMotion;
import com.sanbot.opensdk.function.beans.handmotion.RelativeAngleHandMotion;
import com.sanbot.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.sanbot.opensdk.function.beans.wheelmotion.NoAngleWheelMotion;
import com.sanbot.opensdk.function.beans.wheelmotion.RelativeAngleWheelMotion;
import com.sanbot.opensdk.function.unit.WheelMotionManager;

public class WheelsActivity extends MainActivity implements View.OnClickListener {
    WheelMotionManager wheelMotionManager;
    Button moveForward, turnLeft,noAngleWheelForward;

    @Override
    protected void onMainServiceConnected() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(WheelsActivity.class);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        onMainServiceConnected();
        setContentView(R.layout.wheel_activity);
        wheelMotionManager = (WheelMotionManager) getUnitManager(FuncConstant.WHEELMOTION_MANAGER);

        moveForward = findViewById(R.id.moveForward);
        turnLeft = findViewById(R.id.turnLeft);
        noAngleWheelForward = findViewById(R.id.noAngleWheelMotion);
        moveForward.setOnClickListener(this);
        turnLeft.setOnClickListener(this);
        noAngleWheelForward.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moveForward:
                DistanceWheelMotion distanceWheelMotion = new DistanceWheelMotion(DistanceWheelMotion.ACTION_FORWARD_RUN, 5, 50);
                wheelMotionManager.doDistanceMotion(distanceWheelMotion);
                break;
            case R.id.turnLeft:
                RelativeAngleWheelMotion relativeAngleWheelMotion = new RelativeAngleWheelMotion(RelativeAngleWheelMotion.TURN_LEFT, 5, 45);
                wheelMotionManager.doRelativeAngleMotion(relativeAngleWheelMotion);
                break;
            case R.id.noAngleWheelMotion:
                NoAngleWheelMotion noAngleWheelMotion = new NoAngleWheelMotion(NoAngleWheelMotion.ACTION_FORWARD_RUN,2,3000);
                wheelMotionManager.doNoAngleMotion(noAngleWheelMotion);
                break;
        }

    }
}

