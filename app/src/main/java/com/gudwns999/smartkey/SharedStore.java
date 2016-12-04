package com.gudwns999.smartkey;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kim on 2016-11-19.
 */

public class SharedStore {
    public static String key = "pref";

    // 불린값 획득
    public static boolean getBooolean(Context context, String param) {
        return context.getSharedPreferences(key, 0).getBoolean(param, false);
    }

    // 불린값 저장
    public static void setBooolean(Context context, String param, boolean value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(key, 0).edit();
        editor.putBoolean(param, value);
        editor.commit();
    }

    // 문자열 획득
    public static String getString(Context context, String param) {
        return context.getSharedPreferences(key, 0).getString(param, "");
    }

    // 문자열 저장
    public static void setString(Context context, String param, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(key, 0).edit();
        editor.putString(param, value);
        editor.commit();
    }

    // 정수값 획득
    public static int getInt(Context context, String param) {
        return context.getSharedPreferences(key, 0).getInt(param, 0);
    }

    // 정수값 저장
    public static void setInt(Context context, String param, int value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(key, 0).edit();
        editor.putInt(param, value);
        editor.commit();
    }
}
