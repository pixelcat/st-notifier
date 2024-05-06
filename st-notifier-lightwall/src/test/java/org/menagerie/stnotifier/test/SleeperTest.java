package org.menagerie.stnotifier.test;

import org.junit.Test;
import org.menagerie.stnotifier.renderer.Sleeper;
import org.menagerie.stnotifier.renderer.SleeperImpl;

import java.util.Calendar;

import static junit.framework.TestCase.assertTrue;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 10/21/16, 7:39 PM
 */
public class SleeperTest
{
    @Test
    public void testSleep()
    {
        Sleeper sleeper = new SleeperImpl();
        long startTime = Calendar.getInstance().getTimeInMillis();

        sleeper.doSleep(1000);
        long endTime = Calendar.getInstance().getTimeInMillis();

        assertTrue(endTime > startTime + 800);

    }
}
