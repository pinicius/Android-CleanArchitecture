package com.pinicius.android.ca.skeleton.data.sync.service.push;

/**
 * Created by pinicius on 7/11/14.
 */
public interface PushServiceConsumer {

    interface OnReceivePushMessageHandler {
        void onReceivePushMessage();
    }

    public void connect(String host, String username, String password);

    public void disconnect();

    public void setOnReceivePushMessageHandler(OnReceivePushMessageHandler handler);

    public boolean isConnected();
}