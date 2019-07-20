package com.example.yonariva.sponsorship;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPreferences {

    static final String KEY_USERID = "user_id";
    static final String KEY_USERNAME = "user_name";
    static final String KEY_EMAIL = "user_email";

    public static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUserId(Context context, String pass){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USERID, pass);
        editor.apply();
    }

    public static String getUserId(Context context){
        return getSharedPreference(context).getString(KEY_USERID, "");
    }

    public static void setUserName(Context context, String pass){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_USERNAME, pass);
        editor.apply();
    }

    public static String getUserName(Context context){
        return getSharedPreference(context).getString(KEY_USERNAME, "");
    }

    public static void setUserEmail(Context context, String pass){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(KEY_EMAIL, pass);
        editor.apply();
    }

    public static String getUserEmail(Context context){
        return getSharedPreference(context).getString(KEY_EMAIL, "");
    }


}
