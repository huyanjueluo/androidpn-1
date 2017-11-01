package org.androidpn.client;

import android.content.Context;
import android.content.SharedPreferences;

import com.contron.androidpn.apnbb.Constants;

import org.jivesoftware.smack.packet.IQ;

/**
 * Created by hupei on 2017/11/1.
 */

public final class AndroidpnClient {
    private static AndroidpnClient instance;
    private Notifier notifier;
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor editor;

    public static AndroidpnClient getInstance(Context appContext) {
        // return instance;
        if (instance == null) {
            synchronized (AndroidpnClient.class) {
                instance = new AndroidpnClient(appContext);
            }
        }
        return instance;
    }

    private AndroidpnClient(Context context) {
        notifier = new Notifier(context);
        sharedPrefs = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
    }

    public void notify(IQ iq, String title, String message) {
        notifier.notify(iq, title, message);
    }

    public String getUserName() {
        String username = sharedPrefs.getString(Constants.XMPP_USERNAME, "");
        return username;
    }

    public void setNotificationEnabled(boolean enabled) {
        editor.putBoolean(Constants.SETTINGS_NOTIFICATION_ENABLED, enabled);
        editor.commit();
    }

    public void setNotificationSoundEnabled(boolean enabled) {
        editor.putBoolean(Constants.SETTINGS_SOUND_ENABLED, enabled);
        editor.commit();
    }

    public void setNotificationVibrateEnabled(boolean enabled) {
        editor.putBoolean(Constants.SETTINGS_VIBRATE_ENABLED, enabled);
        editor.commit();
    }

    public boolean isNotificationEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_NOTIFICATION_ENABLED, true);
    }

    public boolean isNotificationSoundEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_SOUND_ENABLED, true);
    }

    public boolean isNotificationVibrateEnabled() {
        return sharedPrefs.getBoolean(Constants.SETTINGS_VIBRATE_ENABLED, true);
    }
}
