package com.pinicius.android.ca.skeleton.data.exception;

/**
 * Created by pinicius on 11/11/14.
 *
 * Exception throw by the application when a there is a push service connection exception.
 */
public class PushServiceConnectionException extends Exception {

    public PushServiceConnectionException(final String message) {super(message);}

    public PushServiceConnectionException(final String message, final Throwable cause) {super(message, cause);}

    public PushServiceConnectionException(final Throwable cause) {super(cause);}
}
