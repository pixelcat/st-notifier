package org.menagerie.stnotifier.console;

import java.io.IOException;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/1/16, 4:30 PM
 */
public interface RenderTarget
{
    void setOn(Character target);

    void setOff(Character target);

    void init();
}
