package com.ksq.com.rxjavaretrofit;

import android.app.Application;
import android.content.Context;

/**
 * Created by 黑白 on 2017/11/13.
 */

public class kApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
