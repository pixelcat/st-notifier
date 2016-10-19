package org.menagerie.stnotifier.video.controller;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/17/16, 11:21 PM
 */
public class PluggableVerificationCodeReceiverImpl implements PluggableVerificationCodeReceiver
{
    private final Lock lock;
    private final Condition gotAuthorizationResponse;
    private volatile String code;
    private volatile String error;

    public PluggableVerificationCodeReceiverImpl()
    {
        this.lock = new ReentrantLock();
        this.gotAuthorizationResponse = this.lock.newCondition();
    }

    @Override public String getRedirectUri() throws IOException
    {
        return "http://localhost:8080/callback";
    }

    @Override public String waitForCode() throws IOException
    {
        this.lock.lock();

        String var1;
        try {
            while(this.code == null && this.error == null) {
                this.gotAuthorizationResponse.awaitUninterruptibly();
            }

            if(this.error != null) {
                throw new IOException("User authorization failed (" + this.error + ")");
            }

            var1 = this.code;
        } finally {
            this.lock.unlock();
        }

        return var1;
    }

    @Override public void stop() throws IOException
    {

    }

    @Override public String getCode()
    {
        return code;
    }

    @Override public void setCode(String code)
    {
        this.code = code;
    }

    @Override public String getError()
    {
        return error;
    }

    @Override public void setError(String error)
    {
        this.error = error;
    }

    @Override public Lock getLock()
    {
        return lock;
    }
}
