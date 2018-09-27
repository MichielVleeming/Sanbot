package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.sanbot.opensdk.base.BindBaseActivity;
import com.sanbot.opensdk.base.TopBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.beans.OperationResult;
import com.sanbot.opensdk.function.unit.ProjectorManager;

public class ProjectorActivity extends TopBaseActivity implements View.OnClickListener {
    ProjectorManager projectorManager;
    Button projectorOpen, closeProjector, checkProjector;
    private int[] mirrorMode = {ProjectorManager.MIRROR_CLOSE, ProjectorManager.MIRROR_LR, ProjectorManager.MIRROR_UD, ProjectorManager.MIRROR_ALL};
    private int[] mode = {ProjectorManager.MODE_WALL, ProjectorManager.MODE_CEILING};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(ProjectorActivity.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projector);

        projectorOpen = findViewById(R.id.startProjector);
        projectorOpen.setOnClickListener(this);
        checkProjector = findViewById(R.id.checkProjector);
        checkProjector.setOnClickListener(this);
        closeProjector = findViewById(R.id.closeProjector);
        closeProjector.setOnClickListener(this);
        projectorManager = (ProjectorManager) getUnitManager(FuncConstant.PROJECTOR_MANAGER);
        projectorManager.setMirror(mirrorMode[0]);
        projectorManager.setTrapezoidH(10);
        projectorManager.setTrapezoidV(10);
        projectorManager.setContrast(10);
        projectorManager.setBright(10);
        projectorManager.setColor(10);
        projectorManager.setSaturation(10);
        projectorManager.setAcuity(10);
        projectorManager.setMode(mode[0]);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.startProjector:
                projectorManager.switchProjector(true);
                projectorManager.queryConfig(ProjectorManager.CONFIG_SWITCH);
                break;
            case R.id.closeProjector:
                projectorManager.switchProjector(false);
                break;
            case R.id.checkProjector:
                projectorManager.queryConfig("abc");
                }
        }

    @Override
    protected void onMainServiceConnected() {

    }
}
