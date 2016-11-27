package org.menagerie.stnotifier.i2c;

import com.pi4j.io.i2c.I2CDevice;
import org.menagerie.stnotifier.console.RenderTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 11/17/16, 8:18 PM
 */
@Component
public class I2CRenderTargetImpl implements RenderTarget
{

    private static final byte[] OSCILLATOR_ON = {0x21};
    private static final byte BRIGHTNESS = (byte)0xE0;
    private static final byte HT16K33_BLINK_CMD = (byte)0x80;
    private static final byte HT16K33_BLINK_DISPLAYON = (byte)0x01;
    private static final byte HT16K33_BLINK_OFF = (byte)0;
    private static final Character[] letters = {
            'a',
            'b',
            'c',
            'd',
            'e',
            'f',
            'g',
            'h',
            'i',
            'j',
            'k',
            'l',
            'm',
            'n',
            'o',
            'p',
            'q',
            'r',
            's',
            't',
            'u',
            'v',
            'w',
            'x',
            'y',
            'z'
    };
    private static final byte[][] registerAddress = {
            {0x00, 0x01}, //a
            {0x00, 0x02}, //b
            {0x00, 0x04}, //c
            {0x00, 0x08}, //d
            {0x10, 0x10}, // e
            {0x20, 0x20}, // f
            {0x40, 0x40}, // g
            {0x00, (byte)0x80}, // h
            {0x01, 0x01}, // i
            {0x01, 0x02}, // j
            {0x01, 0x04}, // k
            {0x01, 0x08}, // l
            {0x01, 0x10}, // m
            {0x01, 0x20}, // n
            {0x01, 0x40}, // o
            {0x01, (byte)0x80}, // p
            {0x02, 0x01}, // q
            {0x02, 0x02}, // r
            {0x02, 0x04}, // s
            {0x02, 0x08}, // t
            {0x02, 0x10}, // u
            {0x02, 0x20}, // v
            {0x02, 0x40}, // w
            {0x02, (byte)0x80}, // x
            {0x04, 0x01}, // y
            {0x04, 0x02} // z
    };
    private static Logger log = LoggerFactory.getLogger(I2CRenderTargetImpl.class);
    private final Map<Character, byte[]> characterMap = new HashMap<>();

    @Value("${menagerie.i2c.ht16k33.address}")
    private int i2cAddress;

    private I2CDevice i2CDevice;

    private I2CDeviceFactory i2CDeviceFactory;

    @Override public void init()
    {
        i2CDevice = i2CDeviceFactory.getDevice(i2cAddress);

        List<byte[]> bytes = Arrays.asList(registerAddress);
        List<Character> chars = Arrays.asList(letters);
        Iterator<byte[]> intIter = bytes.iterator();
        Iterator<Character> characterIterator = chars.iterator();
        while (intIter.hasNext() && characterIterator.hasNext()) {
            Character charAddress = characterIterator.next();
            byte[] bitmap = intIter.next();
            characterMap.put(charAddress, bitmap);
        }

        writeInit(OSCILLATOR_ON);

        setBlinkRate(HT16K33_BLINK_OFF);
        setBrightness(15);
        setOff(' ');

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

    private void writeInit(byte[] ea)
    {
        try {
            i2CDevice.write(ea);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    @Override public void setOn(Character target)
    {
        Character normalizedTarget = Character.toLowerCase(target);

        byte[] bytes = characterMap.get(normalizedTarget);
        if (bytes == null) {
            log.warn("Invalid character passed. Gracefully refusing to print it.");
            return;
        }
        send(bytes);

    }

    private void send(byte[] bytes)
    {
        try {
            byte address = bytes[0];
            byte value = bytes[1];
            i2CDevice.write(address, value);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    @Override public void setOff(Character target)
    {
        byte[][] clear = {
                {0x00, 0x00},
                {0x01, 0x00},
                {0x02, 0x00},
                {0x04, 0x00}
        };
        // zero out register
        for (byte[] aClear : clear) {
            send(aClear);
        }
    }

    public void setI2cAddress(int i2cAddress)
    {
        this.i2cAddress = i2cAddress;
    }

    @Autowired
    public void setI2CDeviceFactory(I2CDeviceFactory i2CDeviceFactory)
    {
        this.i2CDeviceFactory = i2CDeviceFactory;
    }
}
