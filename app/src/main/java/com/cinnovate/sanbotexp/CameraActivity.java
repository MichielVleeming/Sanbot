package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.qihancloud.opensdk.function.unit.MediaManager;
import com.sanbot.opensdk.base.BindBaseService;
import com.sanbot.opensdk.base.TopBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;

public class CameraActivity extends TopBaseActivity{
    private MediaManager mediaManager;

    @Override
    protected void onMainServiceConnected() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(CameraActivity.class);
        super.onCreate(savedInstanceState);
        onMainServiceConnected();
        setBodyView(R.layout.camera_activity);
        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);

    }
}
