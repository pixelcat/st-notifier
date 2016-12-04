package org.menagerie.stnotifier.i2c;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 12/3/16, 4:17 PM
 */
@SuppressWarnings("WeakerAccess") public class STI2CSignal
{

    private int i2cAddress;
    private int register;
    private byte[] value;

    public STI2CSignal(int i2cAddress, int register, byte[] value)
    {
        this.i2cAddress = i2cAddress;
        this.register = register;
        this.value = value;
    }

    public int getI2cAddress()
    {
        return i2cAddress;
    }

    public void setI2cAddress(int i2cAddress)
    {
        this.i2cAddress = i2cAddress;
    }

    public int getRegister()
    {
        return register;
    }

    public void setRegister(int register)
    {
        this.register = register;
    }

    public byte[] getValue()
    {
        return value;
    }

    public void setValue(byte[] value)
    {
        this.value = value;
    }
}
