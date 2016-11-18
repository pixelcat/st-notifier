package org.menagerie.stnotifier.config;

import org.menagerie.stnotifier.model.STConfig;
import org.menagerie.stnotifier.repository.STConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 11/13/16, 3:27 PM
 */
@Component
public class STNotifierConfigImpl implements STNotifierConfig
{
    private STConfigRepository stConfigRepository;

    @Value("${menagerie.stnotifier.wait.betweenMessages}")
    private int defaultWaitBetweenMessages;

    @Value("${menagerie.stnotifier.onTime}")
    private int defaultOnTime;

    @Value("${menagerie.stnotifier.offTime}")
    private int defaultOffTime;

    @Value("${menagerie.stnotifier.wait.start}")
    private int defaultWaitStart;

    @Value("${menagerie.stnotifier.wait.end}")
    private int defaultWaitEnd;

    @Value("${menagerie.stnotifier.wait.space}")
    private int defaultWaitSpace;


    @Override public STConfig getConfig()
    {
        STConfig config = stConfigRepository.findFirstByName("master");
        if (config == null) {
            return initializeDefaultConfig();
        }
        return config;

    }

    @Override public STConfig saveConfig(STConfig config)
    {
        config.setName("master");
        return stConfigRepository.save(config);
    }

    private STConfig initializeDefaultConfig()
    {
        STConfig config = new STConfig();
        config.setName("master");

        config.setWaitBetweenMessages(defaultWaitBetweenMessages);
        config.setOnTime(defaultOnTime);
        config.setOffTime(defaultOffTime);
        config.setWaitSpace(defaultWaitSpace);
        config.setWaitEnd(defaultWaitEnd);
        config.setWaitStart(defaultWaitStart);

        return stConfigRepository.insert(config);
    }

    @Autowired
    public void setStConfigRepository(STConfigRepository stConfigRepository)
    {
        this.stConfigRepository = stConfigRepository;
    }

    public void setDefaultWaitBetweenMessages(int defaultWaitBetweenMessages)
    {
        this.defaultWaitBetweenMessages = defaultWaitBetweenMessages;
    }

    public void setDefaultOnTime(int defaultOnTime)
    {
        this.defaultOnTime = defaultOnTime;
    }

    public void setDefaultOffTime(int defaultOffTime)
    {
        this.defaultOffTime = defaultOffTime;
    }

    public void setDefaultWaitStart(int defaultWaitStart)
    {
        this.defaultWaitStart = defaultWaitStart;
    }

    public void setDefaultWaitEnd(int defaultWaitEnd)
    {
        this.defaultWaitEnd = defaultWaitEnd;
    }

    public void setDefaultWaitSpace(int defaultWaitSpace)
    {
        this.defaultWaitSpace = defaultWaitSpace;
    }
}
