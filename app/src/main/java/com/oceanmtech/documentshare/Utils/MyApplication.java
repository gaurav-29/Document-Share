package com.oceanmtech.documentshare.Utils;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

public class MyApplication extends Application {
    private static MyApplication mInstance;
    public static Context appContext;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        mInstance = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static Context getAppContext() {
        return appContext;
    }
}
