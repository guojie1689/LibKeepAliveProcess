package com.joyveb.lkap;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        BindServicesMananger.getInstance().startServicesWithRestartMode(KeepAliveProcessMain.serviceClass);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
