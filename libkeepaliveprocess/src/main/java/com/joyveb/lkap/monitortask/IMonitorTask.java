package com.joyveb.lkap.monitortask;

import android.app.Service;
import android.content.Context;

/**
 * 监控任务执行状态接口
 */
public interface IMonitorTask {
    void start(Context context, Class<? extends Service> serviceClass);

    void stop(Context context);
}
