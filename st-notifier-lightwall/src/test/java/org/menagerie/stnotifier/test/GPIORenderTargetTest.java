package org.menagerie.stnotifier.test;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.menagerie.stnotifier.gpio.GPIOControllerFactory;
import org.menagerie.stnotifier.gpio.GPIORenderTargetImpl;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 12/4/16, 11:28 AM
 */
public class GPIORenderTargetTest
{
    private Mockery mockery;

    @Before
    public void setUp()
    {
        mockery = new Mockery();
    }

    @Test
    public void testSend()
    {
        GPIORenderTargetImpl renderTarget = new GPIORenderTargetImpl();
        GPIOControllerFactory mockControllerFactory = mockery.mock(GPIOControllerFactory.class);

        GpioController mockController = mockery.mock(GpioController.class);

        GpioPinDigitalOutput com0Pin = mockery.mock(GpioPinDigitalOutput.class, "com0");
        GpioPinDigitalOutput com1Pin = mockery.mock(GpioPinDigitalOutput.class, "com1");
        GpioPinDigitalOutput com2Pin = mockery.mock(GpioPinDigitalOutput.class, "com2");
        GpioPinDigitalOutput com3Pin = mockery.mock(GpioPinDigitalOutput.class, "com3");
        GpioPinDigitalOutput a0Pin = mockery.mock(GpioPinDigitalOutput.class, "a0");
        GpioPinDigitalOutput a1Pin = mockery.mock(GpioPinDigitalOutput.class, "a1");
        GpioPinDigitalOutput a2Pin = mockery.mock(GpioPinDigitalOutput.class, "a2");
        GpioPinDigitalOutput a3Pin = mockery.mock(GpioPinDigitalOutput.class, "a3");
        GpioPinDigitalOutput a4Pin = mockery.mock(GpioPinDigitalOutput.class, "a4");
        GpioPinDigitalOutput a5Pin = mockery.mock(GpioPinDigitalOutput.class, "a5");
        GpioPinDigitalOutput a6Pin = mockery.mock(GpioPinDigitalOutput.class, "a6");
        GpioPinDigitalOutput a7Pin = mockery.mock(GpioPinDigitalOutput.class, "a7");

        mockery.checking(new Expectations()
        {
            {
                oneOf(mockControllerFactory).getController();

                will(returnValue(mockController));

                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_07, "COM0", PinState.HIGH);
                will(returnValue(com0Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_00, "COM1", PinState.HIGH);
                will(returnValue(com1Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_02, "COM2", PinState.HIGH);
                will(returnValue(com2Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_03, "COM3", PinState.HIGH);
                will(returnValue(com3Pin));

                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_15, "A0", PinState.LOW);
                will(returnValue(a0Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_16, "A1", PinState.LOW);
                will(returnValue(a1Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_01, "A2", PinState.LOW);
                will(returnValue(a2Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_04, "A3", PinState.LOW);
                will(returnValue(a3Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_05, "A4", PinState.LOW);
                will(returnValue(a4Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_06, "A5", PinState.LOW);
                will(returnValue(a5Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_10, "A6", PinState.LOW);
                will(returnValue(a6Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_11, "A7", PinState.LOW);
                will(returnValue(a7Pin));

                // expectations for character "f"
                oneOf(com0Pin).low();
                oneOf(a5Pin).high();

                // expect that none of the other pins are set
                never(com1Pin).low();
                never(com2Pin).low();
                never(com3Pin).low();
                never(a0Pin).high();
                never(a1Pin).high();
                never(a2Pin).high();
                never(a3Pin).high();
                never(a4Pin).high();
                never(a6Pin).high();
                never(a7Pin).high();

                // expectations for clear all (setAllOff)
                exactly(2).of(com0Pin).high();
                exactly(2).of(com1Pin).high();
                exactly(2).of(com2Pin).high();
                exactly(2).of(com3Pin).high();
                exactly(2).of(a0Pin).low();
                exactly(2).of(a1Pin).low();
                exactly(2).of(a2Pin).low();
                exactly(2).of(a3Pin).low();
                exactly(2).of(a4Pin).low();
                exactly(2).of(a5Pin).low();
                exactly(2).of(a6Pin).low();
                exactly(2).of(a7Pin).low();

            }
        });
        renderTarget.setGpioControllerFactory(mockControllerFactory);

        renderTarget.init();

        renderTarget.setOn('f');

        renderTarget.setAllOff();

        mockery.assertIsSatisfied();
    }
}
