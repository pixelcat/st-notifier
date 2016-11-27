package org.menagerie.stnotifier.test;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.Before;
import org.junit.Test;
import org.menagerie.stnotifier.binding.VideoStartMessage;
import org.menagerie.stnotifier.config.STNotifierConfig;
import org.menagerie.stnotifier.console.RenderTarget;
import org.menagerie.stnotifier.model.STConfig;
import org.menagerie.stnotifier.model.STMessage;
import org.menagerie.stnotifier.renderer.MessageRendererImpl;
import org.menagerie.stnotifier.renderer.Sleeper;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/21/16, 6:38 PM
 */
public class MessageRendererTest
{
    private Mockery mockery;

    @Before
    public void setUp()
    {
        mockery = new Mockery();
    }

    @Test
    public void testRenderer() throws InterruptedException
    {
        MessageRendererImpl messageRenderer = new MessageRendererImpl();
        Processor mockProcessor = mockery.mock(Processor.class);
        messageRenderer.setProcessor(mockProcessor);

        MessageChannel mockMessageChannel = mockery.mock(MessageChannel.class);

        RenderTarget mockRenderTarget = mockery.mock(RenderTarget.class);
        STNotifierConfig mockNotifierConfig = mockery.mock(STNotifierConfig.class);


        messageRenderer.setStNotifierConfig(mockNotifierConfig);
        messageRenderer.setRenderTarget(mockRenderTarget);
        STConfig config = new STConfig();
        config.setOffTime(50);
        config.setOnTime(500);
        config.setWaitSpace(550);
        config.setWaitStart(1000);
        config.setWaitEnd(900);

        mockery.checking(new Expectations()
        {
            {
                atLeast(1).of(mockNotifierConfig).getConfig();
                will(returnValue(config));
            }
        });
        messageRenderer.setStNotifierConfig(mockNotifierConfig);


        STMessage testMessageSource = new STMessage();
        String testMessage = "test message";
        testMessageSource.setBody(testMessage);

        Sleeper sleeper = mockery.mock(Sleeper.class);
        messageRenderer.setSleeper(sleeper);
        mockery.checking(new Expectations()
        {
            {
                oneOf(mockProcessor).output();
                will(returnValue(mockMessageChannel));

                //noinspection RedundantCast
                oneOf(mockMessageChannel).send(
                        with(allOf(Matchers.hasProperty("payload", (Matcher<?>)aNonNull(VideoStartMessage.class)), aNonNull(GenericMessage.class))));

                // characters
                exactly(11).of(sleeper).doSleep(500);

                Sequence sequence = mockery.sequence("characters");
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('t'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('e'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('s'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('t'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('m'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('e'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('s'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('s'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('a'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('g'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setOn(with(equalTo('e'))); inSequence(sequence);
                exactly(1).of(mockRenderTarget).setAllOff(); inSequence(sequence);

                // spaces
                exactly(1).of(sleeper).doSleep(550);

                // spaces + chars
                exactly(11).of(sleeper).doSleep(50);

                // start time
                exactly(1).of(sleeper).doSleep(1000);

                // end time
                exactly(1).of(sleeper).doSleep(900);
            }
        });
        messageRenderer.render(testMessageSource);

        mockery.assertIsSatisfied();
    }
}
