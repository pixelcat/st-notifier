package org.menagerie.stnotifier.video.messaging.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.menagerie.stnotifier.model.STMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/18/16, 9:32 PM
 */
public class TwilioMessageSenderImpl implements TwilioMessageSender
{
    private static final Logger log = LoggerFactory.getLogger(TwilioMessageSenderImpl.class);
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.account.authToken")
    private String authToken;

    private boolean initialized = false;

    private void init()
    {
        if (!this.initialized) {
            log.info("Account SID: " + accountSid);
            Twilio.init(accountSid, authToken);
            this.initialized = true;
        }

    }

    @Override public void sendResponse(STMessage message, String youtubeURL)
    {
        this.init();
        String body = "Your message has been shown. View it here: " + youtubeURL;
        log.info("Sending sms message to " + message.getFrom() + " from " + message.getTo() + ": " + body);
        Message.creator(new PhoneNumber(message.getFrom()), new PhoneNumber(message.getTo()), body).create();
    }

    public void setAccountSid(String accountSid)
    {
        this.accountSid = accountSid;
    }

    public void setAuthToken(String authToken)
    {
        this.authToken = authToken;
    }
}
