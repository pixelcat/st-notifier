package org.menagerie.stnotifier.console;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 10/1/16, 4:31 PM
 */
@Component
public class SwingRenderTarget implements RenderTarget
{
    private static final Logger log = LoggerFactory.getLogger(SwingRenderTarget.class);

    private final Map<Character, Label> targets = new HashMap<>();

    private SwingTerminalBean swingTerminalBean;

    private MultiWindowTextGUI gui;

    private boolean initialized = false;

    public void init()
    {
        if (!initialized) {
            try {
                String title = "Stranger Things Notifier Demo";
                Screen screen = swingTerminalBean.create(title);

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
                gui = swingTerminalBean.createGui(screen);

                new Thread(() -> gui.addWindowAndWait(window)).start();

                Thread.sleep(2000);
            } catch (InterruptedException | IOException e) {
                log.error("Initialization of dummy terminal failed.", e);
            }
            initialized = true;
        }
    }

    @Override public void setOn(Character target)
    {
        init();
        Label label = targets.get(Character.toLowerCase(target));
        label.setText("*");
        try {
            gui.updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override public void setAllOff()
    {
        init();
        Set<Map.Entry<Character, Label>> entries = targets.entrySet();
        for (Map.Entry<Character, Label> entry : entries) {
            entry.getValue().setText("");
        }
        try {
            gui.updateScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setSwingTerminalBean(SwingTerminalBean swingTerminalBean)
    {
        this.swingTerminalBean = swingTerminalBean;
    }
}
