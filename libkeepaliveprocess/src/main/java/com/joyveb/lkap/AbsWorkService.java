package com.joyveb.lkap;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.joyveb.lkap.uppriority.IUpPriorityStrategy;
import com.joyveb.lkap.uppriority.NotificationUpPriority;

public abstract class AbsWorkService extends Service {

    private IUpPriorityStrategy upPriorityStrategy = new NotificationUpPriority(Config.Abs_Service_Id);

    public abstract void startWork(Intent intent, int flags, int startId);

    public abstract void stopWork();

    public static void cancelMonitorService(Context context) {
        context.sendBroadcast(new Intent(WakeUpReceiver.ACTION_CANCEL_JOB_ALARM_SUB));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        start(intent, 0, 0);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("GJ","AbsWorkService: onStartCommand -- ");
        return start(intent, flags, startId);
    }

    private int start(Intent intent, int flags, int startId) {
        BindServicesMananger.getInstance().startServicesWithRestartMode(DaemonService.class);

        upPriorityStrategy.upPriority(this, InteralNotificationService.class);

        getPackageManager().setComponentEnabledSetting(new ComponentName(getPackageName(), DaemonService.class.getName()),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        startWork(intent, flags, startId);

        return START_STICKY;
    }


    protected void onEnd() {
        stopWork();

        BindServicesMananger.getInstance().startServicesWithRestartMode(KeepAliveProcessMain.serviceClass);
        BindServicesMananger.getInstance().startServicesWithRestartMode(DaemonService.class);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        onEnd();
    }

    @Override
    public void onDestroy() {
        Log.d("GJ", "AbsWorkService: onDestroy ----");
        onEnd();
    }

    public static class InteralNotificationService extends Service {

        /**
         * 利用漏洞在 API Level 18 及以上的 Android 系统中，启动前台服务而不显示通知
         * 运行在:watch子进程中
         */
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(Config.Abs_Service_Id, new Notification());
            stopSelf();
            return START_STICKY;
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
