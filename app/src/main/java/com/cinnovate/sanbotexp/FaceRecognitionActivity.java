package com.cinnovate.sanbotexp;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.qihancloud.opensdk.function.unit.MediaManager;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.FaceRecognizeBean;
import com.sanbot.opensdk.function.unit.interfaces.media.FaceRecognizeListener;
import com.sanbot.opensdk.function.unit.interfaces.media.MediaListener;

import org.json.JSONObject;

import java.util.List;

public class FaceRecognitionActivity extends MainActivity {
    MediaManager mediaManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        register(CameraActivity.class);
        super.onCreate(savedInstanceState);
        onMainServiceConnected();
        setContentView(R.layout.activity_recognition);
        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);
        mediaManager.setMediaListener(new FaceRecognizeListener() {
            @Override
            public void recognizeResult(List<FaceRecognizeBean> list) {
                Log.i("Info123", "Face recognized");
                Log.i("Info123", new Gson().toJson(list));

            }
        });

    }


    @Override
    protected void onMainServiceConnected() {
        super.onMainServiceConnected();
    }
}
