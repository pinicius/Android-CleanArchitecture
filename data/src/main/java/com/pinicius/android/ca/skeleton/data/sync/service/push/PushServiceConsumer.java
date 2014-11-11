package com.pinicius.android.ca.skeleton.data.sync.service.push;

import com.pinicius.android.ca.skeleton.data.exception.PushServiceConnectionException;

/**
 * Created by pinicius on 7/11/14.
 */
public interface PushServiceConsumer {

    interface OnReceivePushMessageHandler {
        void onReceivePushMessage();
    }

    interface ConnectionCallback {
        void onConnectionSuccess();

        void onConnectionError(PushServiceConnectionException exception);
    }

    public void connect(String host, String username, String password, ConnectionCallback callback);

    public void disconnect();

    public void setOnReceivePushMessageHandler(OnReceivePushMessageHandler handler);

    public boolean isConnected();
}