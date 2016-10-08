package org.menagerie.stnotifier.console;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFrame;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/1/16, 4:31 PM
 */
@Component
public class SwingRenderTarget implements RenderTarget
{
    private Map<Character, Label> targets = new HashMap<>();
    private Screen screen;
    private MultiWindowTextGUI gui;

    public void init() throws IOException, InterruptedException
    {
        SwingTerminalFrame terminal = new DefaultTerminalFactory().createSwingTerminal();
        terminal.setTitle("Stranger Things Notifier Demo");
        terminal.setSize(800, 600);
        terminal.setVisible(true);
        terminal.clearScreen();

        screen = new TerminalScreen(terminal);
        screen.startScreen();

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(10));

        String sequence = "abcdefghijklmnopqrstuvwxyz";
        char[] chars = sequence.toCharArray();
        for (char c : chars) {
            Panel subpanel = new Panel();
            subpanel.addComponent(new Label(Character.toString(c)));
            subpanel.setLayoutManager(new GridLayout(1));
            targets.put(c, new Label(""));

            subpanel.addComponent(targets.get(c));
            panel.addComponent(subpanel);
        }
        BasicWindow window = new BasicWindow();
        window.setComponent(panel);
        gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));

        new Thread(() -> gui.addWindowAndWait(window)).start();
        Thread.sleep(2000);
    }

    @Override public void setOn(Character target)
    {
        Label label = targets.get(Character.toLowerCase(target));
        label.setText("*");
        try {
            gui.updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override public void setOff(Character target)
    {
        Label label = targets.get(Character.toLowerCase(target));
        label.setText("");
        try {
            gui.updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
