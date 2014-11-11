package com.pinicius.android.ca.skeleton.data.sync.service;

import android.app.Service;

/**
 * Created by pinicius on 6/11/14.
 */
public abstract class BaseSyncService extends Service {

    public BaseSyncService() {
        super();
        //initializeService();
    }

    abstract void initializeService();
}
