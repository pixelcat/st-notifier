package org.menagerie.stnotifier.video.gphoto2;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 12/9/16, 11:19 AM
 */
public class MockGphoto2CLIImpl implements Gphoto2CLI
{
    private static Logger log = LoggerFactory.getLogger(MockGphoto2CLIImpl.class);

    @Override public void captureMovie(long time, String filePath)
    {
        try {
            File destFile = new File(filePath);
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            URL sourceFile = MockGphoto2CLIImpl.class.getClassLoader().getResource("testvideo.mp4");
            FileUtils.copyFile(new File(sourceFile.getFile()), destFile);
        } catch (IOException e) {
            log.error("", e);
        }

    }
}
