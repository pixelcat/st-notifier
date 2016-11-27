package org.menagerie.stnotifier.i2c;

import com.pi4j.io.i2c.I2CDevice;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 11/23/16, 8:20 AM
 */
public interface I2CDeviceFactory
{
    public I2CDevice getDevice(int deviceAddress);
}
