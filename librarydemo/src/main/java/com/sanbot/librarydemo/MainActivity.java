package com.sanbot.librarydemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.sanbot.librarydemo.R;
import com.sanbot.opensdk.base.TopBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.LED;
import com.sanbot.opensdk.function.beans.handmotion.NoAngleHandMotion;
import com.sanbot.opensdk.function.beans.headmotion.AbsoluteAngleHeadMotion;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.beans.speech.SpeakStatus;
import com.sanbot.opensdk.function.beans.wheelmotion.DistanceWheelMotion;
import com.sanbot.opensdk.function.unit.HandMotionManager;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.HeadMotionManager;
import com.sanbot.opensdk.function.unit.ModularMotionManager;
import com.sanbot.opensdk.function.unit.SpeechManager;
import com.sanbot.opensdk.function.unit.SystemManager;
import com.sanbot.opensdk.function.unit.WheelMotionManager;
import com.sanbot.opensdk.function.unit.interfaces.hardware.InfrareListener;
import com.sanbot.opensdk.function.unit.interfaces.hardware.PIRListener;
import com.sanbot.opensdk.function.unit.interfaces.hardware.TouchSensorListener;
import com.sanbot.opensdk.function.unit.interfaces.hardware.VoiceLocateListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.RecognizeListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.SpeakListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends TopBaseActivity {

    @Bind(R.id.tv_text)
    TextView textTV;
    @Bind(R.id.cb_deal)
    CheckBox dealCb;
    @Bind(R.id.btn_sleep)
    Button sleepBtn;
    @Bind(R.id.tv_wake)
    TextView wakeTv;
    @Bind(R.id.tv_uid)
    TextView uidTv;
    @Bind(R.id.btn_white_light)
    Button whiteLightBtn;
    @Bind(R.id.btn_led)
    Button ledBtn;
    @Bind(R.id.btn_head_move)
    Button headMoveBtn;
    @Bind(R.id.btn_hand_move)
    Button handMoveBtn;
    @Bind(R.id.btn_foot_move)
    Button footMoveBtn;
    @Bind(R.id.tv_touch)
    TextView touchTv;
    @Bind(R.id.btn_service)
    Button serviceBtn;
    @Bind(R.id.tv_pir)
    TextView pirTv;
    @Bind(R.id.tv_voice_locate)
    TextView voiceLocateTv;
    @Bind(R.id.btn_video)
    Button videoBtn;
    @Bind(R.id.tv_volume)
    TextView volumeTv;
    @Bind(R.id.btn_wake)
    Button wakeBtn;
    @Bind(R.id.btn_face_recognize)
    Button faceRecognizeBtn;
    @Bind(R.id.btn_get_speak_status)
    Button speakStatusBtn;
    @Bind(R.id.tv_get_speak_status)
    TextView speakStatusTv;
    @Bind(R.id.btn_speak)
    Button speakBtn;
    @Bind(R.id.tv_infrare_distance)
    TextView infrareDistanceTv;
    @Bind(R.id.btn_light_level_max)
    Button lightMaxBtn;
    @Bind(R.id.btn_light_level_min)
    Button lightMinBtn;
    @Bind(R.id.btn_wander)
    Button wanderBtn;
    @Bind(R.id.btn_pause_speak)
    Button pauseSpeakBtn;
    @Bind(R.id.btn_resume_speak)
    Button resumeSpeakBtn;
    @Bind(R.id.tv_speak_progress)
    TextView speakProgressTv;
    @Bind(R.id.et_head_move)
    EditText headMoveEt;
    @Bind(R.id.et_hand_move)
    EditText handMoveEt;
    @Bind(R.id.et_foot_move)
    EditText footMoveEt;

    MainService mainService;

    boolean isDeal = false;
    boolean isOpen = false;
    SpeechManager speechManager;
    HardWareManager hardWareManager;
    HeadMotionManager headMotionManager;
    HandMotionManager handMotionManager;
    WheelMotionManager wheelMotionManager;
    SystemManager systemManager;
    ModularMotionManager modularMotionManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        register(MainActivity.class);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(128, 128);
        setBodyView(R.layout.activity_main);
        ButterKnife.bind(this);

        speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        headMotionManager = (HeadMotionManager) getUnitManager(FuncConstant.HEADMOTION_MANAGER);
        handMotionManager = (HandMotionManager) getUnitManager(FuncConstant.HANDMOTION_MANAGER);
        wheelMotionManager = (WheelMotionManager) getUnitManager(FuncConstant.WHEELMOTION_MANAGER);
        systemManager = (SystemManager) getUnitManager(FuncConstant.SYSTEM_MANAGER);
        modularMotionManager = (ModularMotionManager) getUnitManager(FuncConstant.MODULARMOTION_MANAGER);

        speechManager.setOnSpeechListener(new RecognizeListener() {
            @Override
            public boolean onRecognizeResult(Grammar grammar) {
                textTV.setText(grammar.getText());
                return isDeal;
            }

            @Override
            public void onRecognizeVolume(int volume) {
                volumeTv.setText(getString(R.string.current_speak_volume) + volume);
            }

            @Override
            public void onStartRecognize() {
                Log.i("Cris", "onStartRecognize: ");
            }

            @Override
            public void onStopRecognize() {
                Log.i("Cris", "onStopRecognize: ");
            }

            @Override
            public void onError(int i, int i1) {
                Log.i("Cris", "onError: i="+i+" i1="+i1);
            }

        });

        Log.i("battery", "battery status is " + systemManager.getBatteryStatus());

        /**
         * 是否截断语音识别内容
         */
        dealCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDeal = isChecked;
            }
        });

        /**
         * 让机器人进入休眠
         */
        sleepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechManager.doSleep();
                //speechManager.startSpeak("开始说话");
            }
        });

        wakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechManager.doWakeUp();
            }
        });

        /**
         * 监听机器人休眠/唤醒状态
         */
        speechManager.setOnSpeechListener(new WakenListener() {
            @Override
            public void onWakeUp() {
                wakeTv.setText(R.string.in_wake_up_status);
            }

            @Override
            public void onSleep() {
                wakeTv.setText(R.string.in_sleep_status);
            }
        });


        /**
         * 获取机器人设备ID
         */
        uidTv.setText(getString(R.string.robot_id) + systemManager.getDeviceId());

        /**
         * 开启、关闭机器人白光灯
         */
        whiteLightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    whiteLightBtn.setText(R.string.close_light);
                    hardWareManager.switchWhiteLight(true);
                } else {
                    whiteLightBtn.setText(R.string.open_light);
                    hardWareManager.switchWhiteLight(false);
                }
                isOpen = !isOpen;
            }
        });

        /**
         * 控制机器人LED灯
         */
        ledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LED led = new LED(LED.PART_LEFT_HEAD, LED.MODE_FLICKER_YELLOW, (byte) 10, (byte) 3);
                hardWareManager.setLED(led);
                //hardWareManager.setLED(new LED(LED.PART_RIGHT_HAND, LED.MODE_RED));
                // mainService.setLed();
            }
        });

        /**
         * 控制机器人头部运动
         */
        headMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int degree = -1;
                if (!TextUtils.isEmpty(headMoveEt.getText().toString())) {
                    degree = Integer.parseInt(headMoveEt.getText().toString());
                }
                AbsoluteAngleHeadMotion absoluteAngleHeadMotion = new AbsoluteAngleHeadMotion(
                        AbsoluteAngleHeadMotion.ACTION_HORIZONTAL, degree >= 0 ? degree : 128
                );
                headMotionManager.doAbsoluteAngleMotion(absoluteAngleHeadMotion);
                /*LocateAbsoluteAngleHeadMotion locateAbsoluteAngleHeadMotion = new LocateAbsoluteAngleHeadMotion(
                        LocateAbsoluteAngleHeadMotion.ACTION_BOTH_LOCK,90,30
                );
                headMotionManager.doAbsoluteLocateMotion(locateAbsoluteAngleHeadMotion);*/
                /*SystemClock.sleep(5000);*/
               /* RelativeAngleHeadMotion relativeAngleHeadMotion = new RelativeAngleHeadMotion(
                        RelativeAngleHeadMotion.ACTION_LEFT,150
                );
                headMotionManager.doRelativeAngleMotion(relativeAngleHeadMotion);*/
            }
        });

        /**
         * 控制机器人手部运动
         */
        handMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoAngleHandMotion leftHandMotion = new NoAngleHandMotion(NoAngleHandMotion.PART_LEFT,
                        5, NoAngleHandMotion.ACTION_UP);
                handMotionManager.doNoAngleMotion(leftHandMotion);
                NoAngleHandMotion rightHandMotion = new NoAngleHandMotion(NoAngleHandMotion.PART_RIGHT,
                        5, NoAngleHandMotion.ACTION_UP);
                handMotionManager.doNoAngleMotion(rightHandMotion);
               /* RelativeAngleHandMotion relativeAngleHandMotion = new RelativeAngleHandMotion(
                        RelativeAngleHandMotion.PART_LEFT,5,RelativeAngleHandMotion.ACTION_DOWN,260
                );
                handMotionManager.doRelativeAngleMotion(relativeAngleHandMotion);*/
               /* int degree = -1;
                if(!TextUtils.isEmpty(handMoveEt.getText().toString())) {
                    degree = Integer.parseInt(handMoveEt.getText().toString());
                }
                AbsoluteAngleHandMotion absoluteAngleHandMotion = new AbsoluteAngleHandMotion(
                        AbsoluteAngleHandMotion.PART_LEFT,5,degree >=0 ? degree : 50
                );
                handMotionManager.doAbsoluteAngleMotion(absoluteAngleHandMotion);*/

            }
        });

        /**
         * 控制机器人轮子运动
         */
        footMoveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int distance = -1;
                if (!TextUtils.isEmpty(footMoveEt.getText().toString())) {
                    distance = Integer.parseInt(footMoveEt.getText().toString());
                }
                DistanceWheelMotion distanceWheelMotion = new DistanceWheelMotion(DistanceWheelMotion.ACTION_FORWARD_RUN,
                        5, distance >= 0 ? distance : 500);
                wheelMotionManager.doDistanceMotion(distanceWheelMotion);

               /* NoAngleWheelMotion noAngleWheelMotion = new NoAngleWheelMotion(NoAngleWheelMotion.ACTION_FORWARD_RUN,
                        (byte)5,500);
                wheelMotionManager.doNoAngleMotion(noAngleWheelMotion);*/
               /* RelativeAngleWheelMotion relativeAngleWheelMotion = new RelativeAngleWheelMotion(
                        RelativeAngleWheelMotion.TURN_LEFT,5,360
                );
                wheelMotionManager.doRelativeAngleMotion(relativeAngleWheelMotion);*/
            }
        });

        /**
         * 监听机器人触摸事件
         */
        hardWareManager.setOnHareWareListener(new TouchSensorListener() {
            @Override
            public void onTouch(int part) {
                switch (part) {
                    case 1:
                        touchTv.setText(R.string.touch_jaw_right);
                        break;
                    case 2:
                        touchTv.setText(R.string.touch_jaw_left);
                        break;
                    case 3:
                        touchTv.setText(R.string.touch_chest_left);
                        break;
                    case 4:
                        touchTv.setText(R.string.touch_chest_right);
                        break;
                    case 5:
                        touchTv.setText(R.string.touch_back_head_left);
                        break;
                    case 6:
                        touchTv.setText(R.string.touch_back_head_right);
                        break;
                    case 7:
                        touchTv.setText(R.string.touch_back_left);
                        break;
                    case 8:
                        touchTv.setText(R.string.touch_back_right);
                        break;
                    case 9:
                        touchTv.setText(R.string.touch_hand_left);
                        break;
                    case 10:
                        touchTv.setText(R.string.touch_hand_right);
                        break;
                    case 11:
                        touchTv.setText(R.string.touch_head_middle);
                        break;
                    case 12:
                        touchTv.setText(R.string.touch_head_left);
                        break;
                    case 13:
                        touchTv.setText(R.string.touch_head_right);
                        break;
                }
            }
        });

        /**
         * 演示在服务中与机器人通讯
         */
        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //systemManager.doHomeAction();
                Intent intent = new Intent(MainActivity.this, MainService.class);
                intent.setPackage(getPackageName());
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
            }
        });


        hardWareManager.setOnHareWareListener(new PIRListener() {
            @Override
            public void onPIRCheckResult(boolean isChecked, int part) {
                if (isChecked) {
                    pirTv.setText(part == 1 ? R.string.trigger_front_pir : R.string.trigger_back_pir);
                }
            }
        });

        hardWareManager.setOnHareWareListener(new VoiceLocateListener() {
            @Override
            public void voiceLocateResult(int angle) {
                voiceLocateTv.setText(getString(R.string.voice_locate_angle) + angle);
            }
        });

        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        faceRecognizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FaceRecognizeActivity.class);
                startActivity(intent);
            }
        });

        speakStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                speakStatusTv.setText(speechManager.isSpeaking()?R.string.robot_is_speaking:R.string.robot_isnot_speaking);
            }
        });

        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechManager.startSpeak("这是一段测试语句");
            }
        });

        hardWareManager.setOnHareWareListener(new InfrareListener() {
            @Override
            public void infrareDistance(int part, int distance) {
                if (part == 13) {
                    infrareDistanceTv.setText(String.format(getString(R.string.infrare_distance),
                            distance));
                }
            }
        });

        lightMaxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hardWareManager.setWhiteLightLevel(3);
            }
        });

        lightMinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hardWareManager.setWhiteLightLevel(1);
            }
        });

        wanderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wanderBtn.getText().equals(getString(R.string.start_wander))) {
                    modularMotionManager.switchWander(true);
                    wanderBtn.setText(R.string.stop_wander);
                } else {
                    wanderBtn.setText(R.string.start_wander);
                    modularMotionManager.switchWander(false);
                }
            }
        });


        pauseSpeakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechManager.pauseSpeak();
            }
        });

        resumeSpeakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechManager.resumeSpeak();
            }
        });

        speechManager.setOnSpeechListener(new SpeakListener() {
            @Override
            public void onSpeakStatus(SpeakStatus speakStatus) {
                if (speakStatus != null) {
                    speakProgressTv.setText(getString(R.string.current_speak_progress) + speakStatus.getProgress());
                }
            }
        });

    }

    @Override
    protected void onMainServiceConnected() {
        boolean isWander = modularMotionManager.getWanderStatus().getResult().equals("1");
        wanderBtn.setText(isWander ? R.string.stop_wander : R.string.start_wander);
        Log.i("log", getString(R.string.mainservice_version) + systemManager.getMainServiceVersion());
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MainService.MyBinder binder = (MainService.MyBinder) service;
            mainService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onDestroy() {
        if (mainService != null) {
            unbindService(connection);
        }
        super.onDestroy();
    }
}
