package com.gudwns999.smartkey;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;

import com.gudwns999.smartkey.Main.Main;

public class Splash extends Activity {
    private SoundPool soundPool;
    private int soundpoolID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        soundPool = new SoundPool(1, AudioManager.STREAM_ALARM,0);
        soundpoolID = soundPool.load(this, R.raw.engine, 1);
        soundPool.play(soundpoolID, 1.0F, 1.0F, 1, 0, 1.0F);
        android.os.Handler handler = new android.os.Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent = new Intent(Splash.this, Main.class);
                startActivity(intent);
                soundPool.stop(soundpoolID);
                finish();
            }
        };
        handler.sendEmptyMessageDelayed(0, 2000);
    }
}
