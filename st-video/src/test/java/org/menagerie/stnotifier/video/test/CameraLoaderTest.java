package org.menagerie.stnotifier.video.test;

import io.codearte.jfairy.Fairy;
import org.junit.Before;
import org.junit.Test;
import org.menagerie.stnotifier.video.gphoto2.Gphoto2CLI;
import org.menagerie.stnotifier.video.gphoto2.Gphoto2CLIImpl;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertTrue;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/16/16, 2:14 PM
 */
public class CameraLoaderTest
{
    @Before
    public void setUp()
    {

    }

    @Test
    public void testCameraList() throws InterruptedException, FileNotFoundException
    {
//        Gphoto2CLI gphoto2CLI = new Gphoto2CLIImpl();
//        Fairy fairy = Fairy.create();
//        fairy.textProducer().randomString(15);
//        File f = new File("testfile-" + fairy.textProducer().randomString(15) + ".mov");
//
//        gphoto2CLI.captureMovie(5000L, f.getAbsolutePath());

        //assertTrue(f.exists());
        assertTrue(true);
    }
}
