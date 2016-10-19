package org.menagerie.stnotifier.tasks;

import org.joda.time.DateTime;
import org.menagerie.stnotifier.model.STMessage;
import org.menagerie.stnotifier.renderer.MessageRenderer;
import org.menagerie.stnotifier.repository.STMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 9/30/16, 10:13 PM
 */
@Component
public class DisplayScheduledTask
{
    private static final Logger log = LoggerFactory.getLogger(DisplayScheduledTask.class);


    private STMessageRepository stMessageRepository;
    private MessageRenderer messageRenderer;

    @Scheduled(fixedDelay = 5000)
    public synchronized void displayMessages() throws InterruptedException
    {
        STMessage message = stMessageRepository.findFirstByDisplayedOrderByReceivedDateDesc(false);
        if (message == null) {
            log.debug("No message to display. Skipping.");
            // no message to display, exit.
            return;
        }

        log.debug("Found message to display: " + message.getBody());

        doDisplayMessage(message);
        message.setDisplayed(true);
        message.setDisplayedDate(DateTime.now());
        stMessageRepository.save(message);
    }

    private void doDisplayMessage(STMessage message) throws InterruptedException
    {
        messageRenderer.render(message);
    }

    @Autowired
    public void setStMessageRepository(@SuppressWarnings("SpringJavaAutowiringInspection") STMessageRepository stMessageRepository)
    {
        this.stMessageRepository = stMessageRepository;
    }

    @Autowired
    public void setMessageRenderer(MessageRenderer messageRenderer)
    {
        this.messageRenderer = messageRenderer;
    }

}
