package org.menagerie.stnotifier.renderer;

import org.menagerie.stnotifier.console.RenderTarget;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Thread.sleep;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/1/16, 4:50 PM
 */
public class SwingMessageRenderer implements MessageRenderer
{
    private RenderTarget renderTarget;

    @Override public void render(String message) throws InterruptedException
    {
        message = message.replaceAll("[^a-zA-Z ]", "");
        for (char c : message.toCharArray()) {
            if (c == ' ') {
                sleep(650);
                continue;
            }
            renderTarget.setOn(c);
            sleep(600);
            renderTarget.setOff(c);
            sleep(50);
        }
    }

    @Autowired
    public void setRenderTarget(RenderTarget renderTarget)
    {
        this.renderTarget = renderTarget;
    }
}
