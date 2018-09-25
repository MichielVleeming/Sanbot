package com.sanbot.librarydemo;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.qihancloud.opensdk.function.unit.MediaManager;
import com.sanbot.opensdk.base.BindBaseService;
import com.sanbot.opensdk.beans.ErrorCode;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.beans.OperationResult;
import com.sanbot.opensdk.function.beans.EmotionsType;
import com.sanbot.opensdk.function.beans.FaceRecognizeBean;
import com.sanbot.opensdk.function.beans.LED;
import com.sanbot.opensdk.function.beans.SpeakOption;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.SpeechManager;
import com.sanbot.opensdk.function.unit.SystemManager;
import com.sanbot.opensdk.function.unit.interfaces.media.FaceRecognizeListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.RecognizeListener;

import java.util.List;

/**
 * MainService.java
 * "Functional Description"
 * <p>
 * Created by 卢杰 on 2016/12/19
 * Copyright (c) 2016 QihanCloud, Inc. All Rights Reserved.
 */
public class MainService extends BindBaseService {
    SpeechManager speechManager;
    SystemManager systemManager;
    HardWareManager hardWareManager;
    MediaManager mediaManager;

    @Override
    public void onCreate() {
        super.onCreate();
        register(MainService.class);
        speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
        systemManager = (SystemManager) getUnitManager(FuncConstant.SYSTEM_MANAGER);
        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        mediaManager = (MediaManager) getUnitManager(FuncConstant.MEDIA_MANAGER);
        speechManager.setOnSpeechListener(new RecognizeListener() {
            @Override
            public boolean onRecognizeResult(Grammar grammar) {
                Log.e("service",grammar.getText());
                return true;
            }

            @Override
            public void onRecognizeVolume(int volume) {

            }

            @Override
            public void onStartRecognize() {

            }

            @Override
            public void onStopRecognize() {

            }

            @Override
            public void onError(int i, int i1) {

            }
        });
        mediaManager.setMediaListener(new FaceRecognizeListener() {
            @Override
            public void recognizeResult(List<FaceRecognizeBean> list) {
                Log.e("pgq", "face:" + new Gson().toJson(list));
            }
        });
    }

    @Override
    protected void onMainServiceConnected() {
        speak();
        //setLed();
        //setEmotion();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        speak();
        //setEmotion();
        setLed();
        return super.onStartCommand(intent, flags, startId);
    }

    void setLed(){
        hardWareManager.setLED(new LED(LED.PART_ALL,LED.MODE_RED));
    }

    void speak(){
        SpeakOption speakOption = new SpeakOption();
        speakOption.setSpeed(70);
        OperationResult operationResult = speechManager.startSpeak("在服务中我也可以和机器人通讯哦",
                speakOption);
        if(operationResult.getErrorCode() != ErrorCode.SUCCESS){
            Log.e("error",operationResult.getDescription());
        }
    }

    /**
     * 表情控制
     */
    void setEmotion(){
        systemManager.showEmotion(EmotionsType.ANGRY);
    }

    public class MyBinder extends Binder {

        public MainService getService(){
            return MainService.this;
        }
    }

    private MyBinder myBinder = new MyBinder();
}
