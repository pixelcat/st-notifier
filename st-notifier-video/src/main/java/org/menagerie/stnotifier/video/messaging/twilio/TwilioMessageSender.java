package org.menagerie.stnotifier.video.messaging.twilio;

import org.menagerie.stnotifier.model.STMessage;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/18/16, 9:31 PM
 */
public interface TwilioMessageSender
{
    void sendResponse(STMessage message, String youtubeURL);
}
