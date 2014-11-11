package com.pinicius.android.ca.skeleton.data.sync.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import com.pinicius.android.ca.skeleton.data.exception.PushServiceConnectionException;
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

    private final Runnable notifyPushServiceConnectionSuccess = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(SyncService.this, "Push Service Connection established", Toast.LENGTH_SHORT).show();
        }
    };

    private final Runnable notifyPushServiceConnectionError = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(SyncService.this, "Error connecting to Push Service", Toast.LENGTH_SHORT).show();
        }
    };

    private PushServiceConsumer.ConnectionCallback pushServiceConnectionCallback = new PushServiceConsumer.ConnectionCallback() {
        @Override
        public void onConnectionSuccess() {
            new Handler(Looper.getMainLooper()).post(notifyPushServiceConnectionSuccess);
        }

        @Override
        public void onConnectionError(PushServiceConnectionException exception) {
            new Handler(Looper.getMainLooper()).post(notifyPushServiceConnectionError);
        }
    };

    private Thread pushServiceConnection = new Thread(new Runnable() {
        @Override
        public void run() {
            Looper.prepare();
            pushConsumer.connect(RabbitMQConsumer.RABBIT_HOST, RabbitMQConsumer.RABBIT_USERNAME, RabbitMQConsumer.RABBIT_PASSWORD, pushServiceConnectionCallback);

        }
    });

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public SyncService() {
        super();
        this.initializeService();
    }

    private final PushServiceConsumer.OnReceivePushMessageHandler onReceivePushMessageHandler = new PushServiceConsumer.OnReceivePushMessageHandler() {
        @Override
        public void onReceivePushMessage() {
            syncManager.syncDataNow();
        }
    };

    @Override
    protected void initializeService() {
        this.pushConsumer = new RabbitMQConsumer();
        this.syncManager = new DataSyncManagerImpl();
        this.pushConsumer.setOnReceivePushMessageHandler(onReceivePushMessageHandler);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        connectConsumer();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.pushConsumer.disconnect();
        pushServiceConnection.destroy();
    }

    private void connectConsumer() {
        if(!pushConsumer.isConnected()) {
            pushServiceConnection.start();
        }
    }
}
