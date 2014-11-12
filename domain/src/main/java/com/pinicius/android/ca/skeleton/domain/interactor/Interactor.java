package com.pinicius.android.ca.skeleton.domain.interactor;

/**
 * Created by pinicius on 12/11/14.
 *
 * Copyright (C) 2014 android10.org. All rights reserved.
 * Fernando Cejas (the android10 coder)
 */
public interface Interactor extends Runnable {
    /**
     * Everything inside this method will be executed asynchronously.
     */
    void run();
}
