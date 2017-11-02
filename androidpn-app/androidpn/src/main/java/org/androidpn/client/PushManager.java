package org.androidpn.client;

import android.content.Context;
import android.content.SharedPreferences;

import com.contron.androidpn.apnbb.Constants;

/**
 * Created by hupei on 2017/11/1.
 */

public final class PushManager {
    private static PushManager instance;
    private boolean initialize;
    private Notifier notifier;
    private SharedPreferences sharedPrefs;

    public static PushManager getInstance() {
        // return instance;
        if (instance == null) {
            synchronized (PushManager.class) {
                instance = new PushManager();
            }
        }
        return instance;
    }

    private PushManager() {
    }

    public void initialize(Context appContext) {
        if (!initialize) {
            initialize = true;
            init(appContext);
            ServiceManager serviceManager = new ServiceManager(appContext);
            serviceManager.startService();
        }
    }

    private void init(Context context) {

        notifier = new Notifier(context);
        sharedPrefs = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

    }

    public void setNotificationIcon(int iconId) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putInt(Constants.NOTIFICATION_ICON, iconId);
        editor.commit();
    }

    public void notify(String title, String message) {
        notifier.notifyLocal(title, message);
    }

    public String getUserName() {
        String username = sharedPrefs.getString(Constants.XMPP_USERNAME, "");
        return username;
    }

    public void setNotificationEnabled(boolean enabled) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(Constants.SETTINGS_NOTIFICATION_ENABLED, enabled);
        editor.commit();
    }

    public void setNotificationSoundEnabled(boolean enabled) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean(Constants.SETTINGS_SOUND_ENABLED, enabled);
        editor.commit();
    }

    public void setNotificationVibrateEnabled(boolean enabled) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
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
