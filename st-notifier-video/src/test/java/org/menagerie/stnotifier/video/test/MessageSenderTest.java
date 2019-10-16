package org.menagerie.stnotifier.video.test;

import org.junit.Test;
import org.menagerie.stnotifier.model.STMessage;
import org.menagerie.stnotifier.video.messaging.twilio.TwilioMessageSenderImpl;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 12/9/16, 12:14 PM
 */
public class MessageSenderTest
{
    @Test
    public void testMessageSend()
    {
        TwilioMessageSenderImpl twilioMessageSender = new TwilioMessageSenderImpl();
        twilioMessageSender.setAccountAuthKey("SK587ef218d310715545098c10e8d885d8");
        twilioMessageSender.setAccountSid("ACcfac9b8e682bc0574fdf46d2abc5997b");
        twilioMessageSender.setAuthToken("8DXrPQpwA4HslnNix1ct7WFUKbNx3HVt");

        STMessage mockMessage = new STMessage();
        mockMessage.setFrom("+19168932758");
        mockMessage.setTo("+19166614222");
        twilioMessageSender.sendResponse(mockMessage, "http://foo.com/");
    }
}
