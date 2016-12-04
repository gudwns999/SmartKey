package com.gudwns999.smartkey.Main;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TabHost;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.gudwns999.smartkey.BlackBox.BlackBox;
import com.gudwns999.smartkey.Control.Control;
import com.gudwns999.smartkey.Home.Home;
import com.gudwns999.smartkey.Locate.Locate;
import com.gudwns999.smartkey.Loging;
import com.gudwns999.smartkey.Parking.teleopclient;
import com.gudwns999.smartkey.R;
import com.gudwns999.smartkey.SharedStore;

/**
 * Created by Kim on 2016-11-19.
 */

public class Main extends TabActivity {

    public static boolean DEBUG = true;
    private FirebaseRemoteConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //thread 정책 바꿈.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Splash에서 넘어온 값 받기
        TabHost tabhost = getTabHost();

        TabHost.TabSpec spec;
        Intent intent;
        Resources res = getResources();

        intent = new Intent().setClass(this, Home.class);
        spec = tabhost.newTabSpec("Home");
        spec.setIndicator("HOME", res.getDrawable(R.drawable.infob));
        spec.setContent(intent);
        tabhost.addTab(spec);

        intent = new Intent().setClass(this, Control.class);
        spec = tabhost.newTabSpec("Controls");
        spec.setIndicator("Controls", res.getDrawable(R.drawable.infob));
        spec.setContent(intent);
        tabhost.addTab(spec);

        intent = new Intent().setClass(this, BlackBox.class);
        spec = tabhost.newTabSpec("BlackBox");
        spec.setIndicator("BlackBox", res.getDrawable(R.drawable.infob));
        spec.setContent(intent);
        tabhost.addTab(spec);

        intent = new Intent().setClass(this, teleopclient.class);
        spec = tabhost.newTabSpec("Parking");
        spec.setIndicator("Parking", res.getDrawable(R.drawable.infob));
        spec.setContent(intent);
        tabhost.addTab(spec);

        intent = new Intent().setClass(this, Locate.class);
        spec = tabhost.newTabSpec("Parking");
        spec.setIndicator("Locate", res.getDrawable(R.drawable.infob));
        spec.setContent(intent);
        tabhost.addTab(spec);
        // 1. config 획득
        config = FirebaseRemoteConfig.getInstance();
        // 2. setting 획득
        FirebaseRemoteConfigSettings configSettings
                = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();

        // 3. 설정
        config.setConfigSettings(configSettings);

        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null) {
            SharedStore.setString(this, "token", token);
            Loging.i(token);
        }
    }

    public boolean isDebuggable(Context context) {
        boolean debuggable = false;
        PackageManager pm = context.getPackageManager();

        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            /* debuggable variable will remain false */
            e.printStackTrace();
        }

        return debuggable;
    }
}
