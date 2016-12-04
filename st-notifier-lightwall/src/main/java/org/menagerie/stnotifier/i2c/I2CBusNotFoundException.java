package org.menagerie.stnotifier.i2c;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 12/3/16, 7:00 PM
 */
@SuppressWarnings("WeakerAccess") public class I2CBusNotFoundException extends RuntimeException
{
    public I2CBusNotFoundException(int i2cAddress)
    {
        super("Address 0x" + Integer.toString(i2cAddress, 16) + " not found.");
    }
}
