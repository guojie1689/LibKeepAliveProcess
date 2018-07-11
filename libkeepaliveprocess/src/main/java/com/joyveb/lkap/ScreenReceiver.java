package com.joyveb.lkap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 屏保
 */
public class ScreenReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Intent intentNew = new Intent(context, LockScreenActivity.class);
            intentNew.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intentNew);
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            LockScreenActivity protectActivity = LockScreenActivity.weakReference != null ? LockScreenActivity.weakReference.get() : null;
            if (protectActivity != null) {
                protectActivity.finish();
            }
        }
    }
}
