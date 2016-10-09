package org.menagerie.stnotifier.test;

import com.github.javafaker.Faker;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.menagerie.stnotifier.model.STMessage;
import org.menagerie.stnotifier.renderer.MessageRenderer;
import org.menagerie.stnotifier.repository.STMessageRepository;
import org.menagerie.stnotifier.tasks.DisplayScheduledTask;

import static junit.framework.TestCase.assertTrue;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/8/16, 10:20 PM
 */
public class DisplayScheduledTaskTest
{
    private Mockery mockery;

    @Before
    public void setUp()
    {
        mockery = new Mockery();
    }

    @Test
    public void testDeliverMessage() throws InterruptedException
    {
        DisplayScheduledTask task = new DisplayScheduledTask();

        MessageRenderer testMessageRenderer = mockery.mock(MessageRenderer.class);
        STMessageRepository stMessageRepository = mockery.mock(STMessageRepository.class);

        STMessage testMessage = new STMessage();
        testMessage.setBody(new Faker().lorem().sentence(3));

        mockery.checking(new Expectations()
        {
            {
                one(stMessageRepository).findFirstByDisplayedOrderByReceivedDateDesc(false);
                will(returnValue(testMessage));

                one(testMessageRenderer).render(testMessage.getBody());
                will(returnValue(null));

                one(stMessageRepository).save(with(testMessage));
                will(returnValue(testMessage));
            }
        });
        task.setMessageRenderer(testMessageRenderer);
        task.setStMessageRepository(stMessageRepository);
        task.displayMessages();
        assertTrue(testMessage.isDisplayed());
        mockery.assertIsSatisfied();
    }
}
