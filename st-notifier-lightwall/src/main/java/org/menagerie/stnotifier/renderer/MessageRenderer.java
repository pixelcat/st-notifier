package org.menagerie.stnotifier.renderer;

import org.menagerie.stnotifier.model.STMessage;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 9/30/16, 10:25 PM
 */
public interface MessageRenderer
{
    void render(STMessage messageSource) throws InterruptedException;
}
