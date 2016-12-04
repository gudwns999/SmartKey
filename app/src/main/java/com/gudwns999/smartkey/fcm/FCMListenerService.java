package com.gudwns999.smartkey.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.gudwns999.smartkey.Loging;
import com.gudwns999.smartkey.Main.Main;
import com.gudwns999.smartkey.R;

/**
 * Created by Kim on 2016-11-19.
 */

public class FCMListenerService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Loging.i("===============FCM MSG===============");
        Loging.i("메시지 수신 : " + remoteMessage.getData().toString());
        String jsonStr = remoteMessage.getData().toString();
        Data msg = new Gson().fromJson(jsonStr, Data.class);
        Loging.i("===============FCM MSG===============");

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, Main.class), PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setTicker("Notification.Builder");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setNumber(10);
        mBuilder.setContentTitle(msg.getTitle());
        mBuilder.setContentText(msg.getMsg());
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setAutoCancel(true);
    }
}