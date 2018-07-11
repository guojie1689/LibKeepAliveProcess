package com.joyveb.lkap;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.HashMap;
import java.util.Map;

public class BindServicesMananger {

    private final Map<Class<? extends Service>, ServiceConnection> BIND_STATE_MAP = new HashMap<>();

    private BindServicesMananger() {

    }

    public static class HOLDER {
        public static BindServicesMananger instance = new BindServicesMananger();
    }

    public static BindServicesMananger getInstance() {
        return BindServicesMananger.HOLDER.instance;
    }

    public synchronized void startServicesWithRestartMode(final Class<? extends Service> serviceClass) {

        final Intent intent = new Intent(KeepAliveProcessMain.context, serviceClass);

        Utils.startService(KeepAliveProcessMain.context, intent);

        ServiceConnection bindConnect = BIND_STATE_MAP.get(serviceClass);

        if (bindConnect == null) {
            KeepAliveProcessMain.context.bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    BIND_STATE_MAP.put(serviceClass, this);
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    BIND_STATE_MAP.remove(serviceClass);
                    Utils.startService(KeepAliveProcessMain.context, intent);

                    KeepAliveProcessMain.context.bindService(intent, this, Context.BIND_AUTO_CREATE);
                }
            }, Context.BIND_AUTO_CREATE);
        }
    }

}
