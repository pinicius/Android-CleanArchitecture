package com.pinicius.android.ca.skeleton.presentation;

import android.app.Application;

import com.pinicius.android.ca.skeleton.data.sync.controller.SyncServiceController;

/**
 * Created by pinicius on 8/11/14.
 */
public class App extends Application {

    private static final String TAG = "App";

    private SyncServiceController syncServiceController;

    @Override
    public void onCreate() {
        super.onCreate();

        syncServiceController = new SyncServiceController(getApplicationContext());
        syncServiceController.startService();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        syncServiceController.stopService();
    }
}
