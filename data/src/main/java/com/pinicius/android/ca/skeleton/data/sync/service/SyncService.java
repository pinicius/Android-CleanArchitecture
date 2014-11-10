package com.pinicius.android.ca.skeleton.data.sync.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.pinicius.android.ca.skeleton.data.sync.DataSyncManager;
import com.pinicius.android.ca.skeleton.data.sync.DataSyncManagerImpl;
import com.pinicius.android.ca.skeleton.data.sync.service.push.PushServiceConsumer;
import com.pinicius.android.ca.skeleton.data.sync.service.push.rabbitmq.RabbitMQConsumer;

/**
 * Created by pinicius on 6/11/14.
 */
public class SyncService extends BaseSyncService {

    private static final String TAG = "SyncService";

    private PushServiceConsumer pushConsumer;
    private DataSyncManager syncManager;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public SyncService() {
        super();
    }

    private PushServiceConsumer.OnReceivePushMessageHandler onReceivePushMessageHandler = new PushServiceConsumer.OnReceivePushMessageHandler() {
        @Override
        public void onReceivePushMessage() {
            syncManager.syncDataNow();
        }
    };

    @Override
    protected void initialize() {
        this.pushConsumer = new RabbitMQConsumer();
        this.syncManager = new DataSyncManagerImpl();
        this.pushConsumer.setOnReceivePushMessageHandler(this.onReceivePushMessageHandler);
        connectConsumer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private Thread rabbitMQConnection = new Thread(new Runnable() {
        @Override
        public void run() {
            pushConsumer.connect(RabbitMQConsumer.RABBIT_HOST, RabbitMQConsumer.RABBIT_USERNAME, RabbitMQConsumer.RABBIT_PASSWORD);
        }
    });

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.pushConsumer.disconnect();
        rabbitMQConnection.destroy();
    }

    private void connectConsumer() {
        if(!rabbitMQConnection.isAlive()) {
            rabbitMQConnection.start();
        }
    }
}
