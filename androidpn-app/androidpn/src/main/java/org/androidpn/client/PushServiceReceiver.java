package org.androidpn.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 用于接收系统消息以保证PushService正常运行
 */
public class PushServiceReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = LogUtil.makeLogTag(PushServiceReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)
                || Intent.ACTION_USER_PRESENT.equals(action)
                || Intent.ACTION_POWER_CONNECTED.equals(action)
                || Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
            Log.d(LOG_TAG, "onReceive：" + action);
            PushManager.getInstance().initialize(context.getApplicationContext());
        }
    }
}
