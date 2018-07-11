package com.joyveb.lkap.uppriority;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;

import com.joyveb.lkap.Utils;

/**
 * 使用Notification提权
 */
public class NotificationUpPriority implements IUpPriorityStrategy {

    private int serviceId = 0;

    public NotificationUpPriority(int serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public void upPriority(Service service, Class<? extends Service> classes) {

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            service.startForeground(serviceId, new Notification());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                Utils.startService(service, new Intent(service, classes));
            }
        }
    }
}
