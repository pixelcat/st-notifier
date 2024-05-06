package org.menagerie.stnotifier.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 12/4/16, 10:10 AM
 */
public class GPIOControllerFactoryImpl implements GPIOControllerFactory
{
    private static GpioController controllerInstance;

    @Override public GpioController getController()
    {
        if (controllerInstance == null) {
            controllerInstance = GpioFactory.getInstance();
        }
        return controllerInstance;
    }
}
