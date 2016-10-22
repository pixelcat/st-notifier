package org.menagerie.stnotifier.console;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;

import java.io.IOException;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/21/16, 5:58 PM
 */
public class SwingTerminalBeanImpl implements SwingTerminalBean
{
    public Screen create(String title) throws IOException
    {
        SwingTerminalFrame terminal = new DefaultTerminalFactory().createSwingTerminal();
        terminal.setTitle(title);
        terminal.setSize(800, 600);
        terminal.setVisible(true);
        terminal.clearScreen();

        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();
        return screen;
    }

    public MultiWindowTextGUI createGui(Screen screen)
    {
        return new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
    }

}
