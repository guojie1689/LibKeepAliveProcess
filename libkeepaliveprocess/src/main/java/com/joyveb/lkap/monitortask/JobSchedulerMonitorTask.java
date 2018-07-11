package com.joyveb.lkap.monitortask;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.joyveb.lkap.Config;
import com.joyveb.lkap.JobSchedulerService;

/**
 * JobScheduler 模式
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerMonitorTask implements IMonitorTask {

    @Override
    public void start(Context context, Class<? extends Service> serviceClass) {
        JobInfo.Builder builder = new JobInfo.Builder(Config.Dameon_Service_Id, new ComponentName(context, JobSchedulerService.class));
        builder.setPeriodic(5000);
        builder.setPersisted(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPeriodic(JobInfo.getMinPeriodMillis(), JobInfo.getMinFlexMillis());
        }

        JobScheduler scheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.schedule(builder.build());
    }

    private static JobScheduler getJobSchedulerService(Context context) {
        return (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Override
    public void stop(Context context) {
        JobScheduler scheduler = getJobSchedulerService(context);
        scheduler.cancel(Config.Dameon_Service_Id);
    }
}
