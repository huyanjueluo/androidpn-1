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
package com.contron.androidpnclient.notify;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.contron.androidpn.apnbb.Constants;
import com.contron.androidpnclient.db.DBIQOperator;

import org.androidpn.client.LogUtil;
import org.androidpn.client.NotificationIQ;
import org.androidpn.client.NotificationReceiver;
import org.androidpn.client.Notifier;


/**
 * Broadcast receiver that handles push notification messages from the server.
 * This should be registered as receiver in AndroidManifest.xml.
 *
 * @author Geek_Solodad (msdx.android@qq.com)
 */
public final class NotifyReceiver extends NotificationReceiver {

    private static final String LOGTAG = LogUtil.makeLogTag(NotifyReceiver.class);

    public NotifyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOGTAG, "NotifyReceiver.onReceive()...");
        String action = intent.getAction();
        Log.d(LOGTAG, "action=" + action);

        if (Constants.ACTION_SHOW_NOTIFICATION.equals(action)) {
            String notificationId = intent.getStringExtra(Constants.NOTIFICATION_ID);
            String notificationApiKey = intent.getStringExtra(Constants.NOTIFICATION_API_KEY);
            String notificationTitle = intent.getStringExtra(Constants.NOTIFICATION_TITLE);
            String notificationMessage = intent.getStringExtra(Constants.NOTIFICATION_MESSAGE);
            String notificationUri = intent.getStringExtra(Constants.NOTIFICATION_URI);
            String packetId = intent.getStringExtra(Constants.PACKET_ID);

            Log.d(LOGTAG, "notificationId=" + notificationId);
            Log.d(LOGTAG, "notificationApiKey=" + notificationApiKey);
            Log.d(LOGTAG, "notificationTitle=" + notificationTitle);
            Log.d(LOGTAG, "notificationMessage=" + notificationMessage);
            Log.d(LOGTAG, "notificationUri=" + notificationUri);
            Log.d(LOGTAG, "packetId=" + packetId);

            Object object = intent.getSerializableExtra(Constants.INTENT_EXTRA_IQ);
            if (object != null && object instanceof NotificationIQ) {
                NotificationIQ iq = (NotificationIQ) object;

                Notifier notifier = new Notifier(context);
                notifier.notify(iq, iq.getTitle(), iq.getMessage());
                saveToDb(context, iq);
            }
        } else if (Constants.ACTION_NOTIFICATION_CLICKED.equals(action)) {
            Intent intentActivity = new Intent(context, NotifyDetailActivity.class);
            intentActivity.putExtras(intent);
            intentActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentActivity);
        }
    }

    private void saveToDb(Context context, NotificationIQ iq) {
        DBIQOperator operator = new DBIQOperator(context);
        operator.saveIQ(iq);
    }

}
