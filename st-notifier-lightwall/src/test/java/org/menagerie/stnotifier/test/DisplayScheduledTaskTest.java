package org.menagerie.stnotifier.test;

import io.codearte.jfairy.Fairy;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.menagerie.stnotifier.config.STNotifierConfig;
import org.menagerie.stnotifier.model.STConfig;
import org.menagerie.stnotifier.model.STMessage;
import org.menagerie.stnotifier.renderer.MessageRenderer;
import org.menagerie.stnotifier.repository.STMessageRepository;
import org.menagerie.stnotifier.tasks.DisplayScheduledTask;

import static junit.framework.TestCase.assertTrue;

/**
 * Copyright 2016 - Kenzi Stewart
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
        Fairy fairy = Fairy.create();
        testMessage.setBody(fairy.textProducer().sentence(3));

        STNotifierConfig stNotifierConfig = mockery.mock(STNotifierConfig.class);

        task.setStNotifierConfig(stNotifierConfig);
        mockery.checking(new Expectations()
        {
            {
                oneOf(stMessageRepository).findFirstByDisplayedAndBlockedOrderByReceivedDateDesc(false, false);
                will(returnValue(testMessage));

                oneOf(testMessageRenderer).render(testMessage);
                will(returnValue(null));

                oneOf(stMessageRepository).save(testMessage);
                will(returnValue(testMessage));

                atLeast(1).of(stNotifierConfig).getConfig();
                will(returnValue(new STConfig()));
            }
        });
        task.setMessageRenderer(testMessageRenderer);
        task.setStMessageRepository(stMessageRepository);
        task.displayMessages();
        assertTrue(testMessage.isDisplayed());
        mockery.assertIsSatisfied();
    }
}
