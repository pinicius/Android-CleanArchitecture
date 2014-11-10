package com.pinicius.android.ca.skeleton.data.sync.controller;

import android.content.Context;
import android.content.Intent;

import com.pinicius.android.ca.skeleton.data.sync.service.SyncService;

/**
 * Created by pinicius on 6/11/14.
 */
public class SyncServiceController implements ServiceController {

    private static final String TAG = "SyncServiceController";

    private Context context;
    private Intent syncServiceIntent;

    public SyncServiceController(Context context) {
        this.context = context;

        this.syncServiceIntent = SyncService.getCallingIntent(this.context);
    }

    @Override
    public void startService() {
        this.context.startService(this.syncServiceIntent);
    }

    @Override
    public void stopService() {
        this.context.stopService(this.syncServiceIntent);
    }
}