package org.menagerie.stnotifier.binding;

import org.menagerie.stnotifier.model.STMessage;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/17/16, 5:23 PM
 */
public class VideoStartMessage
{
    private STMessage stMessage;
    private int duration;

    public STMessage getStMessage()
    {
        return stMessage;
    }

    public void setStMessage(STMessage stMessage)
    {
        this.stMessage = stMessage;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }
}
