package lap.joyveb.com.keepaliveprocess;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.joyveb.lkap.AbsWorkService;

public class MainActivity extends AppCompatActivity {

    private Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        Button btn_start = findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startService(new Intent(context, KeepAliveMonitor.class));
            }
        });

        Button btn_end = findViewById(R.id.btn_end);
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbsWorkService.cancelMonitorService(MainActivity.this);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
