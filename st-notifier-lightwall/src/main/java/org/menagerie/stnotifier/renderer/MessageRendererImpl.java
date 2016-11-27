package org.menagerie.stnotifier.renderer;

import org.menagerie.stnotifier.binding.VideoStartMessage;
import org.menagerie.stnotifier.config.STNotifierConfig;
import org.menagerie.stnotifier.console.RenderTarget;
import org.menagerie.stnotifier.model.STConfig;
import org.menagerie.stnotifier.model.STMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.GenericMessage;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/1/16, 4:50 PM
 */
@EnableBinding(Processor.class)
public class MessageRendererImpl implements MessageRenderer
{
    private RenderTarget renderTarget;

    private STNotifierConfig stNotifierConfig;

    @SuppressWarnings({"SpringJavaAutowiringInspection", "SpringAutowiredFieldsWarningInspection"}) @Autowired
    private Processor processor;

    private Sleeper sleeper;

    public void init() {
        // run a test to validate lights work at boot
        String alpha = "abcdefghijklmnopqrstuvwxyz";

        char[] alphaArray = alpha.toCharArray();
        for (char c : alphaArray) {
            renderTarget.setOn(c);
            sleeper.doSleep(25);
            renderTarget.setOff(c);
        }
        for (int i = alphaArray.length; i > 0; i--) {

            char c = alphaArray[i-1];
            renderTarget.setOn(c);
            sleeper.doSleep(25);
            renderTarget.setOff(c);
        }

    }

    @Override public void render(STMessage messageSource)
    {
        STConfig config = stNotifierConfig.getConfig();

        VideoStartMessage startMessage = new VideoStartMessage();
        String body = messageSource.getBody();
        body = body.replaceAll("[^a-zA-z ]", "");
        startMessage.setDuration(config.getWaitStart() + countChars(body) + config.getWaitEnd());
        startMessage.setStMessage(messageSource);
        processor.output().send(new GenericMessage<>(startMessage));

        String message = messageSource.getBody().replaceAll("[^a-zA-Z ]", "");

        if (config.getWaitStart() > 0) {
            sleeper.doSleep(config.getWaitStart());
        }
        for (char c : message.toCharArray()) {
            if (c == ' ') {
                sleeper.doSleep(config.getWaitSpace());
                continue;
            }
            renderTarget.setOn(c);
            sleeper.doSleep(config.getOnTime());
            renderTarget.setOff(c);
            sleeper.doSleep(config.getOffTime());
        }
        if (config.getWaitEnd() > 0) {
            sleeper.doSleep(config.getWaitEnd());
        }
    }

    @Autowired
    public void setRenderTarget(RenderTarget renderTarget)
    {
        this.renderTarget = renderTarget;
    }

    private int countChars(String message)
    {
        STConfig config = stNotifierConfig.getConfig();

        int duration = 0;
        char[] charMsg = message.toCharArray();
        for (char c : charMsg) {
            if (c == ' ') {
                duration += config.getWaitSpace();
            }
            else {
                duration += config.getOnTime();
            }

            duration += config.getOffTime();
        }
        return duration;
    }

    public void setProcessor(Processor processor)
    {
        this.processor = processor;
    }

    @Autowired
    public void setSleeper(Sleeper sleeper)
    {
        this.sleeper = sleeper;
    }

    @Autowired
    public void setStNotifierConfig(STNotifierConfig stNotifierConfig)
    {
        this.stNotifierConfig = stNotifierConfig;
    }
}
