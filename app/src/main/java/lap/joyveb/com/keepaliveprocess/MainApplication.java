package lap.joyveb.com.keepaliveprocess;

import android.app.Application;
import android.content.Intent;

import com.joyveb.lkap.KeepAliveProcessMain;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        KeepAliveProcessMain.init(this, KeepAliveMonitor.class);

        Intent intent = new Intent(this,KeepAliveMonitor.class);
        startService(intent);
    }
}
