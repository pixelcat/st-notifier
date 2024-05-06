package org.menagerie.stnotifier.gpio;

import com.pi4j.io.gpio.GpioController;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 12/4/16, 10:06 AM
 */
public interface GPIOControllerFactory
{
    public GpioController getController();
}
