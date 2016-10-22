package org.menagerie.stnotifier.support;

import com.google.api.client.http.*;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/19/16, 3:56 PM
 */
@Component
public class ServerLocationNotifierImpl implements ServerLocationNotifier
{
    private static Logger log = LoggerFactory.getLogger(ServerLocationNotifierImpl.class);

    @Value("${notify.server.url}")
    private String notifyServerUrl;

    @Value("${notify.server.username}")
    private String notifyServerUsername;

    @Value("${notify.server.password}")
    private String notifyServerPassword;

    @Autowired
    HttpTransport httpTransport;

    public void notifyServerOfIp() {
        HttpRequestInitializer initializer = new BasicAuthentication(notifyServerUsername, notifyServerPassword);
        HttpRequestFactory requestFactory = httpTransport.createRequestFactory(initializer);
        ServerLocationNotifyBean notifyBean = new ServerLocationNotifyBean();

        try {
            HttpRequest httpRequest = requestFactory.buildPostRequest(new GenericUrl(notifyServerUrl), new UrlEncodedContent(notifyBean));
            HttpResponse response = httpRequest.execute();

        } catch (IOException e) {
            log.error("Cant't notify sever of address information.", e);
        }
    }
}
