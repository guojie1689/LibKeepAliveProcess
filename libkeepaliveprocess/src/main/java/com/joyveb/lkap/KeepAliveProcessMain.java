package com.joyveb.lkap;

import android.app.Service;
import android.content.Context;

public class KeepAliveProcessMain {

    public static Class<? extends Service> serviceClass = null;
    public static Context context;

    public static void init(Context contextParam, Class<? extends Service> serviceClassParam) {
        context = contextParam;
        serviceClass = serviceClassParam;

//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
//        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
//        context.registerReceiver(new ScreenReceiver(), intentFilter);
    }

}
