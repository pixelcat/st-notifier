package org.menagerie.stnotifier.video.gphoto2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/16/16, 3:16 PM
 */
public class Gphoto2CLIImpl implements Gphoto2CLI
{
    private static final Logger log = LoggerFactory.getLogger(Gphoto2CLIImpl.class);

    @Value("${menagerie.video.camera}")
    private String cameraType;

    @Override public void captureMovie(long time, String filePath)
    {
        String gphotoPath;
        try {
            gphotoPath = exec("which gphoto2").replace("\n$", "");

            StringBuilder sb = new StringBuilder();
            sb.append(gphotoPath);
            if ("Nikon-D7100".equals(cameraType)) {
                sb.append(" --list-files");
            }
            sb.append(" --set-config movie=1 --wait-event=").append(time / 1000).append("s --set-config movie=0 --wait-event-and-download=2s --force-overwrite --filename=").append(filePath);

            String output = exec(sb.toString());
            log.info("Output from command:\n" + output);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private String exec(String cmd) throws IOException, InterruptedException
    {
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
        BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));

        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = b.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
