package org.menagerie.stnotifier.renderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/21/16, 6:43 PM
 */
public class SleeperImpl implements Sleeper
{
    private static Logger log = LoggerFactory.getLogger(SleeperImpl.class);
    @Override public void doSleep(int millis)
    {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error("", e);
        }
    }
}
