/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.contron.androidpn.apnbb.Constants;
import com.contron.androidpn.apnbb.NotifierConfig;

import org.jivesoftware.smack.packet.IQ;

/**
 * This class is to notify the user of messages with NotificationManager.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
class Notifier {

    private static final String LOGTAG = LogUtil.makeLogTag(Notifier.class);

    private Context context;

    private SharedPreferences sharedPrefs;

    private NotificationManager notificationManager;
    private static int requestCode = 1000;

    public Notifier(Context context) {
        this.context = context;
        this.sharedPrefs = context.getSharedPreferences(
                Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        this.notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void notify(IQ iq, String title, String message) {
        if (!isNotificationEnabled()) {
            Log.w(LOGTAG, "Notificaitons disabled.");
            return;
        }

        // Notification
        Notification notification = createNotification(message);
        if (NotifierConfig.notifyActivity != null) {
            try {
                Intent intent = new Intent(context, Class.forName(NotifierConfig.notifyActivity));
                intent.putExtra(Constants.INTENT_EXTRA_IQ, iq);
                PendingIntent contentIntent = PendingIntent.getActivity(context, requestCode,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setLatestEventInfo(context, title, message, contentIntent);
                notificationManager.notify(requestCode, notification);
                requestCode++;
            } catch (ClassNotFoundException e) {
                Log.e(LOGTAG, e.getMessage(), e);
            }
        }
    }

    public void notifyLocal(String title, String message) {

        Notification notification = createNotification(message);
        if (NotifierConfig.notifyActivity != null) {
            try {
                Intent intent = new Intent(context, Class.forName(NotifierConfig.notifyActivity));
                PendingIntent contentIntent = PendingIntent.getActivity(context, requestCode,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setLatestEventInfo(context, title, message, contentIntent);
                notificationManager.notify(requestCode, notification);
                requestCode++;
            } catch (ClassNotFoundException e) {
                Log.e(LOGTAG, e.getMessage(), e);
            }
        }
    }

    public void notifyBroadcast(Intent intentSrc) {
        Log.d(LOGTAG, "notifyBroadcast()...");

        if (!isNotificationEnabled()) {
            Log.w(LOGTAG, "Notificaitons disabled.");
            return;
        }

        String title = intentSrc.getStringExtra(Constants.NOTIFICATION_TITLE);
        String message = intentSrc.getStringExtra(Constants.NOTIFICATION_MESSAGE);

        if (isNotificationEnabled()) {
            // Notification
            Notification notification = createNotification(message);

            Intent intentClick = new Intent(Constants.ACTION_NOTIFICATION_CLICKED);
            intentClick.putExtras(intentSrc);

            PendingIntent contentIntent = PendingIntent.getBroadcast(context, requestCode,
                    intentClick, PendingIntent.FLAG_UPDATE_CURRENT);

            notification.setLatestEventInfo(context, title, message, contentIntent);
            notificationManager.notify(requestCode, notification);
            requestCode++;
        } else {
            Log.w(LOGTAG, "Notificaitons disabled.");
        }
    }

    private Notification createNotification(String message) {
        Notification notification = new Notification();
        notification.icon = getNotificationIcon();
        notification.defaults = Notification.DEFAULT_LIGHTS;
        if (isNotificationSoundEnabled()) {
            notification.defaults |= Notification.DEFAULT_SOUND;
        }
        if (isNotificationVibrateEnabled()) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.when = System.currentTimeMillis();
        notification.tickerText = message;
        return notification;
    }

    private int getNotificationIcon() {
        return sharedPrefs.getInt(Constants.NOTIFICATION_ICON, 0);
    }

    private boolean isNotificationEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_NOTIFICATION_ENABLED,
                true);
    }

    private boolean isNotificationSoundEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_SOUND_ENABLED, true);
    }

    private boolean isNotificationVibrateEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_VIBRATE_ENABLED, true);
    }

    private boolean isNotificationToastEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_TOAST_ENABLED, false);
    }

}
