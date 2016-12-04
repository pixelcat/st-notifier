package org.menagerie.stnotifier.test;

import com.pi4j.io.i2c.I2CDevice;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.menagerie.stnotifier.i2c.I2CDeviceFactory;
import org.menagerie.stnotifier.i2c.I2CRenderTargetImpl;

import java.io.IOException;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 11/23/16, 3:12 PM
 */
public class I2CRenderTargetTest
{

    Mockery mockery = new Mockery();

    @Test
    public void testRender() throws IOException
    {
        I2CRenderTargetImpl i2CRenderTarget = new I2CRenderTargetImpl();
        i2CRenderTarget.setI2cAddress("0x70,0x71");
        I2CDeviceFactory mockDeviceFactory = mockery.mock(I2CDeviceFactory.class);
        I2CDevice mockI2cDevice = mockery.mock(I2CDevice.class);
        I2CDevice mockI2cDevice2 = mockery.mock(I2CDevice.class, "mockI2cDevice2");
        mockery.checking(new Expectations()
        {
            {
                exactly(1).of(mockDeviceFactory).getDevice(0x70);
                will(returnValue(mockI2cDevice));

                exactly(1).of(mockDeviceFactory).getDevice(0x71);
                will(returnValue(mockI2cDevice2));
                // assert that initialization took place
                oneOf(mockI2cDevice).write(new byte[]{0x21});
                oneOf(mockI2cDevice2).write(new byte[]{0x21});
                oneOf(mockI2cDevice).write(new byte[]{(byte)0x81});
                oneOf(mockI2cDevice2).write(new byte[]{(byte)0x81});
                oneOf(mockI2cDevice).write(new byte[]{(byte)0xef});
                oneOf(mockI2cDevice2).write(new byte[]{(byte)0xef});

                // character write
                oneOf(mockI2cDevice).write(0x05, new byte[]{0x01});

                for (int i = 0; i < 16; i++) {
                    exactly(2).of(mockI2cDevice).write(i, new byte[]{0x00});
                    exactly(2).of(mockI2cDevice2).write(i, new byte[]{0x00});
                }

            }
        });
        i2CRenderTarget.setI2CDeviceFactory(mockDeviceFactory);
        i2CRenderTarget.init();

        i2CRenderTarget.setOn('f');
        i2CRenderTarget.setAllOff();

        mockery.assertIsSatisfied();
    }
}
