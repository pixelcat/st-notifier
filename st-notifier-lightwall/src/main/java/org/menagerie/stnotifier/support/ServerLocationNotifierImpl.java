package org.menagerie.stnotifier.support;

import org.springframework.beans.factory.annotation.Value;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/19/16, 3:56 PM
 */
public class ServerLocationNotifierImpl implements ServerLocationNotifier
{
    @Value("${notify.server.url}")
    private String notifyServerUrl;

    @Value("${notify.server.username}")
    private String notifyServerUsername;

    @Value("${notify.server.password}")
    private String notifyServerPassword;

    public void notifyServerOfIp() {

    }
}
