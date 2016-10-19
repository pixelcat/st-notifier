package org.menagerie.stnotifier.video.controller;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;

import java.io.IOException;
import java.util.concurrent.locks.Lock;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/18/16, 3:13 PM
 */
public interface PluggableVerificationCodeReceiver extends VerificationCodeReceiver
{
    @Override String getRedirectUri() throws IOException;

    @Override String waitForCode() throws IOException;

    @Override void stop() throws IOException;

    String getCode();

    void setCode(String code);

    String getError();

    void setError(String error);

    Lock getLock();
}
