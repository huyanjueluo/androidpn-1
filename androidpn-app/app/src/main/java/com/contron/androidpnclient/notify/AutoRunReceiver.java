package com.contron.androidpnclient.notify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.androidpn.client.LogUtil;
import org.androidpn.client.PushManager;


public class AutoRunReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = LogUtil.makeLogTag(AutoRunReceiver.class);

    public AutoRunReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Log.d(LOG_TAG, "action_boot_completed");
            PushManager.getInstance().initialize(context.getApplicationContext());
        }
    }
}
