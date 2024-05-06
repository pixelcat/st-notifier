package org.menagerie.stnotifier.test;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import io.codearte.jfairy.Fairy;
import org.junit.Test;
import org.menagerie.stnotifier.console.SwingTerminalBean;
import org.menagerie.stnotifier.console.SwingTerminalBeanImpl;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 10/21/16, 6:30 PM
 */
public class SwingTerminalBeanTest
{
    @Test
    public void testTerminalBean() throws IOException
    {
        SwingTerminalBean swingTerminalBean = new SwingTerminalBeanImpl();
        Fairy fairy = Fairy.create();
        Screen screen = swingTerminalBean.create(fairy.textProducer().latinSentence(4));
        assertNotNull(screen);

        MultiWindowTextGUI gui = swingTerminalBean.createGui(screen);
        assertNotNull(gui);
    }
}
