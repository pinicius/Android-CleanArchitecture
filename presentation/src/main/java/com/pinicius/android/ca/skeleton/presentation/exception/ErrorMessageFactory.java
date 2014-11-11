/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.pinicius.android.ca.skeleton.presentation.exception;

import android.content.Context;

import com.pinicius.android.ca.skeleton.data.exception.PushServiceConnectionException;
import com.pinicius.android.ca.skeleton.presentation.R;

/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

  private ErrorMessageFactory() {
    //empty
  }

  /**
   * Creates a String representing an error message.
   *
   * @param context Context needed to retrieve string resources.
   * @param exception An exception used as a condition to retrieve the correct error message.
   * @return {@link String} an error message.
   */
  public static String create(Context context, Exception exception) {
    String message = context.getString(R.string.exception_message_generic);

    if (exception instanceof PushServiceConnectionException) {
      message = "There is a push service error connection";
        }

        return message;
        }
        }
