package com.pinicius.android.ca.skeleton.data.sync.service.push.rabbitmq;

import com.pinicius.android.ca.skeleton.data.exception.PushServiceConnectionException;
import com.pinicius.android.ca.skeleton.data.sync.service.push.PushServiceConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * Created by pinicius on 7/11/14.
 */
public class RabbitMQConsumer implements PushServiceConsumer {

    private static final String TAG = "RabbitMQServiceConnection";

    public static final String RABBIT_HOST = "zerb01.baibalab.net";
    private static final String RABBIT_VHOST = "bizistats";
    private static final int RABBIT_PORT = 5672;
    public static final String RABBIT_USERNAME = "read";
    public static final String RABBIT_PASSWORD = "read";
    private static final String RABBIT_QUEUE_NAME = "bizistats-q";

    private Connection connection;
    private Channel channel;
    private OnReceivePushMessageHandler pushMessageHandler;

    private ConnectionCallback callback;

    private boolean connected = false;


    public RabbitMQConsumer() {
        //empty
    }

    @Override
    public void connect(String host, String user, String password, ConnectionCallback callback) {
        this.callback = callback;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setVirtualHost(RABBIT_VHOST);
        factory.setPort(RABBIT_PORT);
        factory.setUsername(user);
        factory.setPassword(password);
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            boolean autoAck = false;
            //String queueName = channel.queueDeclare().getQueue();
            String queueName = RABBIT_QUEUE_NAME;
            channel.basicConsume(queueName, autoAck, "myConsumerTag",
                    new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag,
                                                   Envelope envelope,
                                                   AMQP.BasicProperties properties,
                                                   byte[] body)
                                throws IOException
                        {
                            pushMessageHandler.onReceivePushMessage();
                            long deliveryTag = envelope.getDeliveryTag();
                            channel.basicAck(deliveryTag, false);

                            /*String routingKey = envelope.getRoutingKey();
                            String contentType = properties.getContentType();
                            long deliveryTag = envelope.getDeliveryTag();
                            // (process the message components here ...)
                            channel.basicAck(deliveryTag, false);*/
                        }
                    });
            this.connected = true;
            this.callback.onConnectionSuccess();
        }
        catch (IOException e) {
            this.callback.onConnectionError(new PushServiceConnectionException(e.getCause()));
        }
    }

    @Override
    public void disconnect() {
        try {
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOnReceivePushMessageHandler(OnReceivePushMessageHandler handler) {
        this.pushMessageHandler = handler;
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }

}
