package com.joyveb.lkap.monitortask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.joyveb.lkap.Config;
import com.joyveb.lkap.KeepAliveProcessMain;

public class AlarmManagerMonitorTask implements IMonitorTask {

    private PendingIntent sPendingIntent;

    @Override
    public void start(Context context, Class<? extends Service> serviceClass) {
        AlarmManager am = getAlarmService(context);

        Intent intent = new Intent(context, KeepAliveProcessMain.serviceClass);

        sPendingIntent = PendingIntent.getService(context, Config.Dameon_Service_Id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + Config.checkInterval, Config.checkInterval, sPendingIntent);
    }

    private static AlarmManager getAlarmService(Context context) {
        return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void stop(Context context) {
        AlarmManager am = getAlarmService(context);

        if (sPendingIntent != null)
            am.cancel(sPendingIntent);
    }
}
