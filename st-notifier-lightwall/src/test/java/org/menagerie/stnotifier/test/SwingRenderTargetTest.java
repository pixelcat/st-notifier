package org.menagerie.stnotifier.test;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.TerminalScreen;
import io.codearte.jfairy.Fairy;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.menagerie.stnotifier.console.SwingRenderTarget;
import org.menagerie.stnotifier.console.SwingTerminalBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/21/16, 6:03 PM
 */
public class SwingRenderTargetTest
{
    private static Logger log = LoggerFactory.getLogger(SwingRenderTargetTest.class);

    Mockery mockery;

    @Before
    public void setUp() {
        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
        mockery.setThreadingPolicy(new Synchroniser());
    }
    @Test
    public void testRender() throws IOException
    {
        SwingRenderTarget swingRenderTarget = new SwingRenderTarget();

        SwingTerminalBean mockSwingTerminalBean = mockery.mock(SwingTerminalBean.class);
        TerminalScreen mockTerminalScreen = mockery.mock(TerminalScreen.class);
        MultiWindowTextGUI mockGui = mockery.mock(MultiWindowTextGUI.class);
        mockery.checking(new Expectations() {
            {
                oneOf(mockSwingTerminalBean).create("Stranger Things Notifier Demo");
                will(returnValue(mockTerminalScreen));

                oneOf(mockSwingTerminalBean).createGui(with(same(mockTerminalScreen)));

                will(returnValue(mockGui));

                oneOf(mockGui).addWindowAndWait(with(aNonNull(Window.class)));
                exactly(2).of(mockGui).updateScreen();
            }
        });

        swingRenderTarget.setSwingTerminalBean(mockSwingTerminalBean);

        Fairy fairy = Fairy.create();

        char randomChar = fairy.textProducer().randomString(1).charAt(0);

        log.info("Rendering character: " + randomChar);
        swingRenderTarget.setOn(randomChar);

        swingRenderTarget.setOff(randomChar);

    }
}
