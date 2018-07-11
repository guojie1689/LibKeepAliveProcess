package lap.joyveb.com.keepaliveprocess;

import android.content.Intent;
import android.util.Log;

import com.joyveb.lkap.AbsWorkService;

public class KeepAliveMonitor extends AbsWorkService {

    private boolean isFirstRun = true;

    @Override
    public void startWork(Intent intent, int flags, int startId) {
        Log.d("GJ", "startWork ----");

        if (isFirstRun) {
            isFirstRun = false;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Log.d("GJ", "KeepAliveMonitor ----");

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    @Override
    public void stopWork() {
        Log.d("GJ", "stopWork ----");
    }
}
