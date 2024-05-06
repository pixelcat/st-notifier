package org.menagerie.stnotifier.console;

import java.io.IOException;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 10/1/16, 4:30 PM
 */
public interface RenderTarget
{
    void setOn(Character target);

    void setAllOff();

    void init();
}
