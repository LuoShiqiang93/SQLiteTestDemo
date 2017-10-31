package com.mark.sqlitetestdemo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LuoShiQiang on 2017/10/31
 * SharedPreferences 管理器
 */

public class PreferenceManager {
    public static final String PREFERENCE_NAME = "saveInfo";
    private static SharedPreferences mSharedPreferences;
    private static PreferenceManager mPreferencemManager;
    private static SharedPreferences.Editor editor;

    private static String SHARED_KEY_CURRENTUSER_USERNAME = "SHARED_KEY_CURRENTUSER_USERNAME";

    @SuppressLint("CommitPrefEdits")
    private PreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public synchronized static PreferenceManager getInstance() {
        if (mSharedPreferences == null) {
            throw new RuntimeException("please init first!");
        }
        return mPreferencemManager;
    }

    public static synchronized void init(Context context){
        if (mPreferencemManager==null){
            mPreferencemManager=new PreferenceManager(context);
        }
    }

    public void setCurrentUserName(String userName) {
        editor.putString(SHARED_KEY_CURRENTUSER_USERNAME, userName);
        editor.apply();
    }

    public String getCurrentUsername() {
        return mSharedPreferences.getString(SHARED_KEY_CURRENTUSER_USERNAME, null);
    }
}
