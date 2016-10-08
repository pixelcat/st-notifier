package org.menagerie.stnotifier.renderer;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 9/30/16, 10:25 PM
 */
public interface MessageRenderer
{
    void render(String message) throws InterruptedException;
}
