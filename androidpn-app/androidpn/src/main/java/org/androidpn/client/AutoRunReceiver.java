package org.androidpn.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 开机自启广播，用于启动推送服务
 */
public class AutoRunReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = LogUtil.makeLogTag(AutoRunReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            Log.d(LOG_TAG, "action_boot_completed");
            PushManager.getInstance().initialize(context.getApplicationContext());
        }
    }
}
