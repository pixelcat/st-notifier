package org.menagerie.stnotifier.video.test;

import org.junit.Test;
import org.menagerie.stnotifier.model.STMessage;
import org.menagerie.stnotifier.video.messaging.twilio.TwilioMessageSenderImpl;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 12/9/16, 12:14 PM
 */
public class MessageSenderTest
{
    @Test
    public void testMessageSend()
    {
        TwilioMessageSenderImpl twilioMessageSender = new TwilioMessageSenderImpl();
        twilioMessageSender.setAccountAuthKey("<authkey>");
        twilioMessageSender.setAccountSid("<accountsid>");
        twilioMessageSender.setAuthToken("<authtoken>");

        STMessage mockMessage = new STMessage();
        mockMessage.setFrom("<yourphone>");
        mockMessage.setTo("<twiliophone>");
        twilioMessageSender.sendResponse(mockMessage, "http://foo.com/");
    }
}
