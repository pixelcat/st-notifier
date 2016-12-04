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
 * Copyright 2016 - Aaron Stewart
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

                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_04, "COM0", PinState.LOW);
                will(returnValue(com0Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_17, "COM1", PinState.LOW);
                will(returnValue(com1Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_27, "COM2", PinState.LOW);
                will(returnValue(com2Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_22, "COM3", PinState.LOW);
                will(returnValue(com3Pin));

                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_14, "A0", PinState.LOW);
                will(returnValue(a0Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_15, "A1", PinState.LOW);
                will(returnValue(a1Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_18, "A2", PinState.LOW);
                will(returnValue(a2Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_23, "A3", PinState.LOW);
                will(returnValue(a3Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_24, "A4", PinState.LOW);
                will(returnValue(a4Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_25, "A5", PinState.LOW);
                will(returnValue(a5Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_08, "A6", PinState.LOW);
                will(returnValue(a6Pin));
                oneOf(mockController).provisionDigitalOutputPin(RaspiPin.GPIO_07, "A7", PinState.LOW);
                will(returnValue(a7Pin));

                oneOf(com0Pin).high();
                oneOf(a5Pin).high();
                never(com1Pin).high();
                never(com2Pin).high();
                never(com3Pin).high();
                never(a0Pin).high();
                never(a1Pin).high();
                never(a2Pin).high();
                never(a3Pin).high();
                never(a4Pin).high();

                never(a6Pin).high();
                never(a7Pin).high();

                oneOf(com0Pin).low();
                oneOf(com1Pin).low();
                oneOf(com2Pin).low();
                oneOf(com3Pin).low();
                oneOf(a0Pin).low();
                oneOf(a1Pin).low();
                oneOf(a2Pin).low();
                oneOf(a3Pin).low();
                oneOf(a4Pin).low();
                oneOf(a5Pin).low();
                oneOf(a6Pin).low();
                oneOf(a7Pin).low();

            }
        });
        renderTarget.setGpioControllerFactory(mockControllerFactory);

        renderTarget.init();

        renderTarget.setOn('f');

        renderTarget.setAllOff();

        mockery.assertIsSatisfied();
    }
}
