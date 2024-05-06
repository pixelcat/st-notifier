package org.menagerie.stnotifier.console;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 10/21/16, 5:59 PM
 */
public interface SwingTerminalBean
{
    Screen create(String title) throws IOException;

    MultiWindowTextGUI createGui(Screen screen);

}
