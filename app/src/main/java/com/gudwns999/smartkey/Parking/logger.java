package com.gudwns999.smartkey.Parking;

import android.os.Handler;
import android.widget.TextView;

/**
 * Created by Kim on 2016-11-19.
 */

class ps implements Runnable{
    TextView t;
    String s;
    public ps(TextView t, String s){
        this.t = t;
        this.s = s;
    }
    public void run(){
        t.setText(s);
    }
}

public class logger {
    Handler h;
    TextView t;
    public logger(TextView t){
        this.t = t;
        h = new Handler();
    }
    public void log(String s){
        h.post(new ps(t, s));
    }
}
