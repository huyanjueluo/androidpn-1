package com.contron.androidpnclient;

import android.app.Application;

import org.androidpn.client.PushManager;

/**
 * Created by hupei on 2017/11/2.
 */

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PushManager.getInstance().initialize(this.getApplicationContext());
    }
}
