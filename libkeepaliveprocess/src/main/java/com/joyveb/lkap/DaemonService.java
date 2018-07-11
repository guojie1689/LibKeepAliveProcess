package com.joyveb.lkap;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.joyveb.lkap.monitortask.AlarmManagerMonitorTask;
import com.joyveb.lkap.monitortask.IMonitorTask;
import com.joyveb.lkap.monitortask.JobSchedulerMonitorTask;
import com.joyveb.lkap.uppriority.IUpPriorityStrategy;
import com.joyveb.lkap.uppriority.NotificationUpPriority;

/**
 * 守护进程
 */
public class DaemonService extends Service {

    private static IMonitorTask monitorTask = null;
    private Binder binder = new Binder();

    private IUpPriorityStrategy upPriorityStrategy = new NotificationUpPriority(Config.Dameon_Service_Id);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        start();

        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("GJ", "DaemonService: onStartCommand -- ");

        return start();
    }

    private int start() {

        upPriorityStrategy.upPriority(this, InteralNotificationService.class);

        startCheckServiceRunning();

        //守护 Service 组件的启用状态, 使其不被 MAT 等工具禁用
        getPackageManager().setComponentEnabledSetting(new ComponentName(getPackageName(), KeepAliveProcessMain.serviceClass.getName()),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        return START_STICKY;
    }

    private void startCheckServiceRunning() {
        //Android 5.0+ 使用 JobScheduler
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            monitorTask = new JobSchedulerMonitorTask();
        } else { //AlarmManager
            monitorTask = new AlarmManagerMonitorTask();
        }

        monitorTask.start(this, KeepAliveProcessMain.serviceClass);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("GJ", "DaemonService: onDestroy ----");

//        BindServicesMananger.getInstance().startServicesWithRestartMode(KeepAliveProcessMain.serviceClass);
//        BindServicesMananger.getInstance().startServicesWithRestartMode(DaemonService.class);
    }

    public static void stopDamenService(Context context) {
        if (monitorTask != null) {
            monitorTask.stop(context);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopDamenService(this);
    }

    public static class InteralNotificationService extends Service {

        /**
         * 利用漏洞在 API Level 18 及以上的 Android 系统中，启动前台服务而不显示通知
         * 运行在:watch子进程中
         */
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(Config.Dameon_Service_Id, new Notification());
            stopSelf();
            return START_STICKY;
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
