package org.menagerie.stnotifier.i2c;

import jdk.dio.DeviceManager;
import jdk.dio.i2cbus.I2CDevice;
import jdk.dio.i2cbus.I2CDeviceConfig;
import org.menagerie.stnotifier.console.RenderTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 11/17/16, 8:18 PM
 */
@Component
public class I2CRenderTargetImpl implements RenderTarget
{

    @Value("${menagerie.i2c.bus}")
    private int i2cBus;

    @Value("${menagerie.i2c.ht16k33.address}")
    private int i2cAddress;

    private static Logger log = LoggerFactory.getLogger(I2CRenderTargetImpl.class);

    private I2CDeviceConfig i2CDeviceConfig;

    private static final byte[] OSCILLATOR_ON = {0x21};
    private static final byte BRIGHTNESS = (byte)0xE0;

    private static final byte HT16K33_BLINK_CMD = (byte)0x80;
    private static final byte HT16K33_BLINK_DISPLAYON = (byte)0x01;

    private static final byte HT16K33_BLINK_OFF = (byte)0;

    private final Map<Character, int[]> characterMap = new HashMap<>();

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
    private static final int[][] addresses = {
            {0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, //a
            {0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, //b
            {0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, //c
            {0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, //d
            {0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // e
            {0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // f
            {0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // g
            {0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // h
            {0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // i
            {0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // j
            {0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // k
            {0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // l
            {0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // m
            {0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // n
            {0x00, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // o
            {0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // p
            {0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // q
            {0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // r
            {0x00, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // s
            {0x00, 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // t
            {0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // u
            {0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // v
            {0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // w
            {0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // x
            {0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, // y
            {0x00, 0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00} // z
    };

    @Override public void init()
    {
        List<int[]> ints = Arrays.asList(addresses);
        List<Character> chars = Arrays.asList(letters);
        Iterator<int[]> intIter = ints.iterator();
        Iterator<Character> characterIterator = chars.iterator();
        while (intIter.hasNext() && characterIterator.hasNext()) {
            Character charAddress = characterIterator.next();
            int[] bitmap = intIter.next();
            characterMap.put(charAddress, bitmap);
        }

        i2CDeviceConfig = new I2CDeviceConfig(i2cBus, i2cAddress, I2CDeviceConfig.ADDR_SIZE_7, 100000);
        writeInit(OSCILLATOR_ON);

        setBlinkRate(HT16K33_BLINK_OFF);
        setBrightness(15);
    }


    private void setBrightness(int b)
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
        try (I2CDevice i2CDevice = DeviceManager.open(i2CDeviceConfig)) {

            ByteBuffer blinkRateCmd = ByteBuffer.wrap(ea);
            i2CDevice.write(blinkRateCmd);
            i2CDevice.close();

        } catch (IOException ioe) {
            log.error("", ioe);
        }
    }

    @Override public void setOn(Character target)
    {
        send(characterMap.get(target));

    }

    private void send(int[] bytes)
    {
        try (I2CDevice i2CDevice = DeviceManager.open(i2CDeviceConfig)) {
            i2CDevice.write(0x00, 0x08, ByteBuffer.wrap(Arrays.asList(bytes).toString().getBytes()));
            i2CDevice.close();
        } catch (IOException ioe) {
            log.error("", ioe);
        }
    }

    @Override public void setOff(Character target)
    {
        int[] clear = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        send(clear);
    }

    public void setI2cBus(int i2cBus)
    {
        this.i2cBus = i2cBus;
    }

    public void setI2cAddress(int i2cAddress)
    {
        this.i2cAddress = i2cAddress;
    }
}
