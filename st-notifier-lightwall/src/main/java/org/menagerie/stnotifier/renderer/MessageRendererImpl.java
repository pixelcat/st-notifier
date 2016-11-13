package org.menagerie.stnotifier.renderer;

import org.menagerie.stnotifier.binding.VideoStartMessage;
import org.menagerie.stnotifier.console.RenderTarget;
import org.menagerie.stnotifier.model.STMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${menagerie.stnotifier.onTime}")
    private int onTime = 0;

    @Value("${menagerie.stnotifier.offTime}")
    private int offTime = 0;

    @Value("${menagerie.stnotifier.wait.start}")
    private int waitStart = 0;

    @Value("${menagerie.stnotifier.wait.end}")
    private int waitEnd = 0;

    @Value("${menagerie.stnotifier.wait.space}")
    private int waitSpace = 0;

    @SuppressWarnings({"SpringJavaAutowiringInspection", "SpringAutowiredFieldsWarningInspection"}) @Autowired
    private Processor processor;

    private Sleeper sleeper;

    @Override public void render(STMessage messageSource)
    {
        VideoStartMessage startMessage = new VideoStartMessage();
        String body = messageSource.getBody();
        body = body.replaceAll("[^a-zA-z ]", "");
        startMessage.setDuration(waitStart + countChars(body) + waitEnd);
        startMessage.setStMessage(messageSource);
        processor.output().send(new GenericMessage<>(startMessage));

        String message = messageSource.getBody().replaceAll("[^a-zA-Z ]", "");

        if (waitStart > 0) {
            sleeper.doSleep(waitStart);
        }
        for (char c : message.toCharArray()) {
            if (c == ' ') {
                sleeper.doSleep(waitSpace);
                continue;
            }
            renderTarget.setOn(c);
            sleeper.doSleep(onTime);
            renderTarget.setOff(c);
            sleeper.doSleep(offTime);
        }
        if (waitEnd > 0) {
            sleeper.doSleep(waitEnd);
        }
    }

    @Autowired
    public void setRenderTarget(RenderTarget renderTarget)
    {
        this.renderTarget = renderTarget;
    }

    private int countChars(String message)
    {
        int duration = 0;
        char[] charMsg = message.toCharArray();
        for (char c : charMsg) {
            if (c == ' ') {
                duration += waitSpace;
            }
            else {
                duration += onTime;
            }

            duration += offTime;
        }
        return duration;
    }


    public void setOnTime(int onTime)
    {
        this.onTime = onTime;
    }

    public void setOffTime(int offTime)
    {
        this.offTime = offTime;
    }

    public void setWaitStart(int waitStart)
    {
        this.waitStart = waitStart;
    }

    public void setWaitEnd(int waitEnd)
    {
        this.waitEnd = waitEnd;
    }

    public void setWaitSpace(int waitSpace)
    {
        this.waitSpace = waitSpace;
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
}
