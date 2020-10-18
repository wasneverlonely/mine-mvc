package com.was.minemvc.common;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.was.core.utils.ToastUtils;

/**
 * application
 */
public class App extends MultiDexApplication {

    static Application app;

    public static Application getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
//        Thread.setDefaultUncaughtExceptionHandler(new RestartHandler(this)); // 程序崩溃时触发线程
        ToastUtils.register(getApplicationContext());
    }

}
