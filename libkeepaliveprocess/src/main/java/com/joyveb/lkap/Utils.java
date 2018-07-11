package com.joyveb.lkap;

import android.content.Context;
import android.content.Intent;

public class Utils {

    public static void startService(Context context, Intent intent) {
        try {
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
