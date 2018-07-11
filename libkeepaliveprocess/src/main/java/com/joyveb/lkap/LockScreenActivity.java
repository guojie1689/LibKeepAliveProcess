package com.joyveb.lkap;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

public class LockScreenActivity extends Activity {

    public static WeakReference<LockScreenActivity> weakReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        weakReference = new WeakReference<>(this);

        settingWindow();
    }

    private void settingWindow() {
        Window window = getWindow();

        //放在左上角
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        //起始坐标
        params.x = 0;
        params.y = 0;
        //宽高设计为1个像素
        params.height = 1;
        params.width = 1;
        params.alpha = 0;
        window.setAttributes(params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        closeActivity();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        closeActivity();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isScreenOn()) {
            closeActivity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (weakReference != null && weakReference.get() == this) {
            weakReference = null;
        }
    }

    /**
     * 判断主屏幕是否点亮
     *
     * @return
     */
    private boolean isScreenOn() {
        PowerManager powerManager = (PowerManager) getApplicationContext()
                .getSystemService(Context.POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            return powerManager.isInteractive();
        } else {
            return powerManager.isScreenOn();
        }
    }

    private void closeActivity() {
        if (!isFinishing()) {
            finish();
        }
    }
}
