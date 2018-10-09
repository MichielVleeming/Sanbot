package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.sanbot.opensdk.base.TopBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.unit.ProjectorManager;

public class ProjectorActivity extends TopBaseActivity implements View.OnClickListener {
    ProjectorManager projectorManager;
    Button projectorOpen, closeProjector, checkProjector;
    ImageView projectorImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(ProjectorActivity.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projector);

        projectorManager = (ProjectorManager) getUnitManager(FuncConstant.PROJECTOR_MANAGER);

        onMainServiceConnected();
        projectorImage = findViewById(R.id.projectorImage);
        projectorOpen = findViewById(R.id.startProjector);
        projectorOpen.setOnClickListener(this);
        checkProjector = findViewById(R.id.checkProjector);
        checkProjector.setOnClickListener(this);
        closeProjector = findViewById(R.id.closeProjector);
        closeProjector.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.startProjector:
                projectorManager.switchProjector(true);

                break;
            case R.id.closeProjector:
                projectorManager.switchProjector(false);
                break;
            case R.id.checkProjector:
                Glide.with(this)
                        .load("https://upload.wikimedia.org/wikipedia/commons/6/66/Android_robot.png")
                        .into(projectorImage);
                projectorImage.setVisibility(View.VISIBLE);

        }
    }

    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            default:
                projectorManager.setMirror(ProjectorManager.MIRROR_CLOSE);
                projectorManager.setMode(ProjectorManager.MODE_WALL);
                break;
            case R.id.radio_wall:
                if (checked)
                    projectorManager.setMode(ProjectorManager.MODE_WALL);
                break;
            case R.id.radio_ceiling:
                if (checked)
                    projectorManager.setMode(ProjectorManager.MODE_CEILING);
                break;
            case R.id.radio_close_mirror:
                if (checked)
                    projectorManager.setMirror(ProjectorManager.MIRROR_CLOSE);
                break;
            case R.id.radio_mirror:
                if (checked)
                    projectorManager.setMirror(ProjectorManager.MIRROR_ALL);
                break;
            case R.id.radio_LR_mirror:
                if (checked)
                    projectorManager.setMirror(ProjectorManager.MIRROR_LR);
                break;
            case R.id.radio_UD_mirror:
                if (checked)
                    projectorManager.setMirror(ProjectorManager.MIRROR_UD);
                break;

        }
    }

    @Override
    protected void onMainServiceConnected() {

    }
}
