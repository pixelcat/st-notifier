package org.menagerie.stnotifier.i2c;

import com.pi4j.io.i2c.I2CDevice;
import org.menagerie.stnotifier.console.RenderTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 11/17/16, 8:18 PM
 */
@Component
public class I2CRenderTargetImpl implements RenderTarget
{
    private static Logger log = LoggerFactory.getLogger(I2CRenderTargetImpl.class);

    private static final byte[] OSCILLATOR_ON = {0x21};
    private static final byte BRIGHTNESS = (byte)0xE0;
    private static final byte HT16K33_BLINK_CMD = (byte)0x80;
    private static final byte HT16K33_BLINK_DISPLAYON = (byte)0x01;

    private static final byte HT16K33_BLINK_OFF = (byte)0;

    private final Map<Character, STI2CSignal> signals = new HashMap<>();

    @Value("${menagerie.i2c.ht16k33.address}")
    private String i2cAddress;

    private Map<Integer, I2CDevice> i2cDevices = new HashMap<>();

    private I2CDeviceFactory i2CDeviceFactory;

    @Override public void init()
    {
        String[] addresses = i2cAddress.split(",[ ]*");
        for (String address : addresses) {
            int addrInt = Integer.decode(address);
            i2cDevices.put(addrInt, i2CDeviceFactory.getDevice(addrInt));
        }

        setupSignalsByRegister();

        writeInit(OSCILLATOR_ON);

        setBlinkRate(HT16K33_BLINK_OFF);
        setBrightness(15);
        setAllOff();

    }

    private void setupSignalsByRegister()
    {
        signals.put('a', new STI2CSignal(0x70, 0x00, new byte[]{0x01}));
        signals.put('b', new STI2CSignal(0x70, 0x01, new byte[]{0x01}));
        signals.put('c', new STI2CSignal(0x70, 0x02, new byte[]{0x01}));
        signals.put('d', new STI2CSignal(0x70, 0x03, new byte[]{0x01}));
        signals.put('e', new STI2CSignal(0x70, 0x04, new byte[]{0x01}));
        signals.put('f', new STI2CSignal(0x70, 0x05, new byte[]{0x01}));
        signals.put('g', new STI2CSignal(0x70, 0x06, new byte[]{0x01}));
        signals.put('h', new STI2CSignal(0x70, 0x07, new byte[]{0x01}));

        signals.put('i', new STI2CSignal(0x70, 0x08, new byte[]{0x01}));
        signals.put('j', new STI2CSignal(0x70, 0x09, new byte[]{0x01}));
        signals.put('k', new STI2CSignal(0x70, 0x0A, new byte[]{0x01}));
        signals.put('l', new STI2CSignal(0x70, 0x0B, new byte[]{0x01}));
        signals.put('m', new STI2CSignal(0x70, 0x0C, new byte[]{0x01}));
        signals.put('n', new STI2CSignal(0x70, 0x0D, new byte[]{0x01}));
        signals.put('o', new STI2CSignal(0x70, 0x0E, new byte[]{0x01}));
        signals.put('p', new STI2CSignal(0x70, 0x0F, new byte[]{0x01}));

        signals.put('q', new STI2CSignal(0x71, 0x00, new byte[]{0x01}));
        signals.put('r', new STI2CSignal(0x71, 0x01, new byte[]{0x01}));
        signals.put('s', new STI2CSignal(0x71, 0x02, new byte[]{0x01}));
        signals.put('t', new STI2CSignal(0x71, 0x03, new byte[]{0x01}));
        signals.put('u', new STI2CSignal(0x71, 0x04, new byte[]{0x01}));
        signals.put('v', new STI2CSignal(0x71, 0x05, new byte[]{0x01}));
        signals.put('w', new STI2CSignal(0x71, 0x06, new byte[]{0x01}));
        signals.put('x', new STI2CSignal(0x71, 0x07, new byte[]{0x01}));
        signals.put('u', new STI2CSignal(0x71, 0x08, new byte[]{0x01}));
        signals.put('z', new STI2CSignal(0x71, 0x09, new byte[]{0x01}));
    }

    private void setupSignalsByValue()
    {
        signals.put('a', new STI2CSignal(0x70, 0x01, new byte[]{0x00, 0x01}));
        signals.put('b', new STI2CSignal(0x70, 0x01, new byte[]{0x00, 0x02}));
        signals.put('c', new STI2CSignal(0x70, 0x01, new byte[]{0x00, 0x04}));
        signals.put('d', new STI2CSignal(0x70, 0x01, new byte[]{0x00, 0x08}));
        signals.put('e', new STI2CSignal(0x70, 0x01, new byte[]{0x00, 0x10}));
        signals.put('f', new STI2CSignal(0x70, 0x01, new byte[]{0x00, 0x20}));
        signals.put('g', new STI2CSignal(0x70, 0x01, new byte[]{0x00, 0x40}));
        signals.put('h', new STI2CSignal(0x70, 0x01, new byte[]{0x00, (byte)0x80}));

        signals.put('i', new STI2CSignal(0x70, 0x01, new byte[]{0x01, 0x00}));
        signals.put('j', new STI2CSignal(0x70, 0x01, new byte[]{0x02, 0x00}));
        signals.put('k', new STI2CSignal(0x70, 0x01, new byte[]{0x04, 0x00}));
        signals.put('l', new STI2CSignal(0x70, 0x01, new byte[]{0x08, 0x00}));

        signals.put('m', new STI2CSignal(0x70, 0x01, new byte[]{0x10, 0x00}));
        signals.put('n', new STI2CSignal(0x70, 0x01, new byte[]{0x20, 0x00}));
        signals.put('o', new STI2CSignal(0x70, 0x01, new byte[]{0x40, 0x00}));
        signals.put('p', new STI2CSignal(0x70, 0x01, new byte[]{(byte)0x80, 0x00}));

        signals.put('q', new STI2CSignal(0x71, 0x01, new byte[]{0x00, 0x01}));
        signals.put('r', new STI2CSignal(0x71, 0x01, new byte[]{0x00, 0x02}));
        signals.put('s', new STI2CSignal(0x71, 0x01, new byte[]{0x00, 0x04}));
        signals.put('t', new STI2CSignal(0x71, 0x01, new byte[]{0x00, 0x08}));
        signals.put('u', new STI2CSignal(0x71, 0x01, new byte[]{0x00, 0x10}));
        signals.put('v', new STI2CSignal(0x71, 0x01, new byte[]{0x00, 0x20}));

        signals.put('w', new STI2CSignal(0x71, 0x01, new byte[]{0x00, 0x40}));
        signals.put('x', new STI2CSignal(0x71, 0x01, new byte[]{0x00, (byte)0x80}));
        signals.put('u', new STI2CSignal(0x71, 0x01, new byte[]{0x01, 0x00}));
        signals.put('z', new STI2CSignal(0x71, 0x01, new byte[]{0x02, 0x00}));
    }


    private void setBrightness(@SuppressWarnings("SameParameterValue") int b)
    {

        if (b > 15) {
            b = 15;
        }
        else if (b < 0) {
            b = 0;
        }

        byte[] ea = {(byte)(BRIGHTNESS | b)};

        writeInit(ea);
    }

    private void setBlinkRate(int b)
    {

        if (b > 3) {
            b = 0; // turn off if not sure
        }
        else if (b < 0) {
            b = 0;
        }

        byte[] ea =
                {(byte)(HT16K33_BLINK_CMD | HT16K33_BLINK_DISPLAYON | (b << 1))};

        writeInit(ea);
    }

    private void writeInit(byte[] initValue)
    {
        try {
            Collection<I2CDevice> i2cDeviceList = this.i2cDevices.values();
            for (I2CDevice device : i2cDeviceList) {
                device.write(initValue);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    @Override public void setOn(Character target)
    {
        Character normalizedTarget = Character.toLowerCase(target);

        STI2CSignal sti2CSignal = signals.get(normalizedTarget);
        if (sti2CSignal == null) {
            log.warn("Invalid character passed. Gracefully refusing to print it.");
            return;
        }
        send(sti2CSignal);

    }

    private void send(STI2CSignal signal)
    {
        try {
            byte register = (byte)signal.getRegister();

            byte[] value = signal.getValue();
            I2CDevice i2CDevice = i2cDevices.get(signal.getI2cAddress());
            if (i2CDevice == null) {
                throw new I2CBusNotFoundException(signal.getI2cAddress());
            }
            i2CDevice.write(register, value);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    @Override public void setAllOff()
    {
        for (int i = 0; i < 16; i++) {
            for (Integer address : i2cDevices.keySet()) {
                send(new STI2CSignal(address, i, new byte[]{0x00}));
            }
        }

    }

    public void setI2cAddress(@SuppressWarnings("SameParameterValue") String i2cAddress)
    {
        this.i2cAddress = i2cAddress;
    }

    @Autowired
    public void setI2CDeviceFactory(I2CDeviceFactory i2CDeviceFactory)
    {
        this.i2CDeviceFactory = i2CDeviceFactory;
    }
}
