package org.menagerie.stnotifier.gpio;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import org.menagerie.stnotifier.console.RenderTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 12/4/16, 10:14 AM
 */
public class GPIORenderTargetImpl implements RenderTarget
{
    private static Logger log = LoggerFactory.getLogger(GPIORenderTargetImpl.class);

    @Autowired
    private GPIOControllerFactory gpioControllerFactory;

    @SuppressWarnings("FieldCanBeLocal") private GpioController controller;

    private Map<String, GpioPinDigitalOutput> gpioPorts = new HashMap<>();

    private Map<Character, String[]> signals = new HashMap<>();

    @Override public void setOn(Character target)
    {
        char adjustedTarget = Character.toLowerCase(target);

        if (!signals.containsKey(adjustedTarget)) {
            log.warn("Invalid character passed. Gracefully refusing to print it.");
            return;
        }
        String[] ports = signals.get(adjustedTarget);
        for (String portName : ports) {
            if (!gpioPorts.containsKey(portName)) {
                log.warn("invalid port specified.");
                return;
            }
            GpioPinDigitalOutput gpioPort = gpioPorts.get(portName);
            if (portName.startsWith("COM")) {
                gpioPort.low();
            }
            else {
                gpioPort.high();
            }
        }

    }

    @Override public void setAllOff()
    {
        Set<Map.Entry<String, GpioPinDigitalOutput>> entries = gpioPorts.entrySet();
        for (Map.Entry<String, GpioPinDigitalOutput> entry : entries) {
            GpioPinDigitalOutput port = entry.getValue();
            port.low();
        }
    }

    @Override public void init()
    {
        controller = gpioControllerFactory.getController();

        gpioPorts.put("COM0", controller.provisionDigitalOutputPin(RaspiPin.GPIO_04, "COM0", PinState.LOW));
        gpioPorts.put("COM1", controller.provisionDigitalOutputPin(RaspiPin.GPIO_17, "COM1", PinState.LOW));
        gpioPorts.put("COM2", controller.provisionDigitalOutputPin(RaspiPin.GPIO_27, "COM2", PinState.LOW));
        gpioPorts.put("COM3", controller.provisionDigitalOutputPin(RaspiPin.GPIO_22, "COM3", PinState.LOW));

        gpioPorts.put("A0", controller.provisionDigitalOutputPin(RaspiPin.GPIO_14, "A0", PinState.LOW));
        gpioPorts.put("A1", controller.provisionDigitalOutputPin(RaspiPin.GPIO_15, "A1", PinState.LOW));
        gpioPorts.put("A2", controller.provisionDigitalOutputPin(RaspiPin.GPIO_18, "A2", PinState.LOW));
        gpioPorts.put("A3", controller.provisionDigitalOutputPin(RaspiPin.GPIO_23, "A3", PinState.LOW));
        gpioPorts.put("A4", controller.provisionDigitalOutputPin(RaspiPin.GPIO_24, "A4", PinState.LOW));
        gpioPorts.put("A5", controller.provisionDigitalOutputPin(RaspiPin.GPIO_25, "A5", PinState.LOW));
        gpioPorts.put("A6", controller.provisionDigitalOutputPin(RaspiPin.GPIO_08, "A6", PinState.LOW));
        gpioPorts.put("A7", controller.provisionDigitalOutputPin(RaspiPin.GPIO_07, "A7", PinState.LOW));

        signals.put('a', new String[]{"COM0", "A0"});
        signals.put('b', new String[]{"COM0", "A1"});
        signals.put('c', new String[]{"COM0", "A2"});
        signals.put('d', new String[]{"COM0", "A3"});
        signals.put('e', new String[]{"COM0", "A4"});
        signals.put('f', new String[]{"COM0", "A5"});
        signals.put('g', new String[]{"COM0", "A6"});
        signals.put('h', new String[]{"COM0", "A7"});
        signals.put('i', new String[]{"COM1", "A0"});

        signals.put('j', new String[]{"COM1", "A1"});
        signals.put('k', new String[]{"COM1", "A2"});
        signals.put('l', new String[]{"COM1", "A3"});
        signals.put('m', new String[]{"COM1", "A4"});
        signals.put('n', new String[]{"COM1", "A5"});
        signals.put('o', new String[]{"COM1", "A6"});
        signals.put('p', new String[]{"COM1", "A7"});

        signals.put('q', new String[]{"COM2", "A0"});
        signals.put('r', new String[]{"COM2", "A1"});
        signals.put('s', new String[]{"COM2", "A2"});
        signals.put('t', new String[]{"COM2", "A3"});
        signals.put('u', new String[]{"COM2", "A4"});
        signals.put('v', new String[]{"COM2", "A5"});
        signals.put('w', new String[]{"COM2", "A6"});
        signals.put('x', new String[]{"COM2", "A7"});

        signals.put('y', new String[]{"COM3", "A0"});
        signals.put('z', new String[]{"COM3", "A1"});

        setAllOff();
    }

    public void setGpioControllerFactory(GPIOControllerFactory gpioControllerFactory)
    {
        this.gpioControllerFactory = gpioControllerFactory;
    }
}
