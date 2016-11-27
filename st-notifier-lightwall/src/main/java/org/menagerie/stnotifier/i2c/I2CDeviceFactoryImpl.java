package org.menagerie.stnotifier.i2c;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 11/23/16, 8:21 AM
 */
@Component
public class I2CDeviceFactoryImpl implements I2CDeviceFactory
{
    private static Logger log = LoggerFactory.getLogger(I2CDeviceFactoryImpl.class);

    private final Map<Integer, I2CDevice> devices = new HashMap<>();

    private I2CBus i2cBus;

    @Value("${menagerie.i2c.bus}")
    private int i2cBusNumber;

    public void init() {
        try {
            i2cBus = I2CFactory.getInstance(i2cBusNumber);

        } catch (I2CFactory.UnsupportedBusNumberException | IOException e) {
            log.error("", e);
        }
    }

    @Override public I2CDevice getDevice(int deviceAddress)
    {
        try {
            if (!devices.containsKey(deviceAddress)) {
                I2CDevice i2CDevice = i2cBus.getDevice(deviceAddress);
                devices.put(deviceAddress, i2CDevice);
            }
            return devices.get(deviceAddress);
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    public void setI2cBusNumber(int i2cBusNumber)
    {
        this.i2cBusNumber = i2cBusNumber;
    }
}
