package com.mark.sqlitetestdemo;

import android.app.Application;

import com.mark.sqlitetestdemo.utils.PreferenceManager;

/**
 * Created by LuoShiQiang on 2017/10/31
 */

public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        PreferenceManager.init(getApplicationContext());
    }
}
