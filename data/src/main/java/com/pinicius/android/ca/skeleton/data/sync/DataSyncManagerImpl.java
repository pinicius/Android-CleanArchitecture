package com.pinicius.android.ca.skeleton.data.sync;

import android.util.Log;


/**
 * Created by pinicius on 6/11/14.
 */
public class DataSyncManagerImpl implements DataSyncManager {

    private static final String TAG = "DataSyncManagerImpl";

    public DataSyncManagerImpl() {
        // empty
    }

    @Override
    public void syncDataNow() {
        Log.d(TAG, "Synchronizing data...");
    }

}