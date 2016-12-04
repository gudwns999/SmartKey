package com.gudwns999.smartkey.fcm;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.gudwns999.smartkey.Loging;
import com.gudwns999.smartkey.SharedStore;

/**
 * Created by Kim on 2016-11-19.
 */

public class InstanceListenerService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String token = FirebaseInstanceId.getInstance().getToken();
        SharedStore.setString(this, "token", token);
        Loging.i(token);
    }
}