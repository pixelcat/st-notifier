package org.menagerie.stnotifier.renderer;

import com.google.common.collect.Lists;
import org.menagerie.stnotifier.binding.VideoStartMessage;
import org.menagerie.stnotifier.config.STNotifierConfig;
import org.menagerie.stnotifier.console.RenderTarget;
import org.menagerie.stnotifier.model.STConfig;
import org.menagerie.stnotifier.model.STMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.support.GenericMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 10/1/16, 4:50 PM
 */
@EnableBinding(Processor.class)
public class MessageRendererImpl implements MessageRenderer
{
    private static Logger log = LoggerFactory.getLogger(MessageRendererImpl.class);
    private RenderTarget renderTarget;

    private STNotifierConfig stNotifierConfig;

    @SuppressWarnings({"SpringJavaAutowiringInspection", "SpringAutowiredFieldsWarningInspection"}) @Autowired
    private Processor processor;

    private Sleeper sleeper;

    public void init() {
        // run a test to validate lights work at boot
        String alpha = "abcdefghijklmnopqrstuvwxyz";

//        List<Character> alphaList = alpha.chars().mapToObj(c -> (char)c).collect(Collectors.toList());
//        for (char c : alphaList) {
//            renderTarget.setOn(c);
//            sleeper.doSleep(100);
//            renderTarget.setAllOff();
//        }
//        List<Character> revAlpha = Lists.reverse(alphaList);
//        for (char c : revAlpha) {
//            renderTarget.setOn(c);
//            sleeper.doSleep(100);
//            renderTarget.setAllOff();
//        }
//        renderTarget.setAllOff();
    }

    @Override public void render(STMessage messageSource)
    {
        log.info("Displaying message: " + messageSource.getBody() + ".");
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
            renderTarget.setAllOff();
            sleeper.doSleep(config.getOffTime());
        }
        if (config.getWaitEnd() > 0) {
            sleeper.doSleep(config.getWaitEnd());
        }
        log.info("Finished displaying message.");
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
