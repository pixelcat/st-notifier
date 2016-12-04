package org.menagerie.stnotifier.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 11/13/16, 3:18 PM
 */
@Document(collection = "config")
public class STConfig
{
    private ObjectId id;
    private String name;

    private int waitBetweenMessages;
    private int onTime;
    private int offTime;
    private int waitSpace;
    private int waitEnd;
    private int waitStart;
    private boolean paused = false;
    private int rateLimit;

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getWaitBetweenMessages()
    {
        return waitBetweenMessages;
    }

    public void setWaitBetweenMessages(int waitBetweenMessages)
    {
        this.waitBetweenMessages = waitBetweenMessages;
    }

    public void setOnTime(int waitOnTime)
    {
        this.onTime = waitOnTime;
    }

    public void setOffTime(int waitOffTime)
    {
        this.offTime = waitOffTime;
    }

    public void setWaitSpace(int waitSpace)
    {
        this.waitSpace = waitSpace;
    }

    public void setWaitEnd(int waitEnd)
    {
        this.waitEnd = waitEnd;
    }

    public void setWaitStart(int waitStart)
    {
        this.waitStart = waitStart;
    }

    public int getOnTime()
    {
        return onTime;
    }

    public int getOffTime()
    {
        return offTime;
    }

    public int getWaitSpace()
    {
        return waitSpace;
    }

    public int getWaitEnd()
    {
        return waitEnd;
    }

    public int getWaitStart()
    {
        return waitStart;
    }

    public boolean isPaused()
    {
        return paused;
    }

    public void setPaused(boolean paused)
    {
        this.paused = paused;
    }

    public int getRateLimit()
    {
        return rateLimit;
    }

    public void setRateLimit(int rateLimit)
    {
        this.rateLimit = rateLimit;
    }

}
