package org.menagerie.stnotifier.video.test;

import com.google.api.services.youtube.model.Video;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.menagerie.stnotifier.video.youtube.OAuth2Adapter;
import org.menagerie.stnotifier.video.youtube.YoutubeUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/16/16, 5:15 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class YoutubeUploaderTest
{
    @Autowired
    YoutubeUploader youtubeUploader;

    @Autowired
    OAuth2Adapter oAuth2Adapter;

    @Test
    public void testUpload() throws IOException
    {
        Video video = youtubeUploader.uploadVideo("/Users/acs/develop/st-notifier/st-video/testfile-lqjwxsokermjuoj.mov", "test message");

        assertNotNull(video);

        oAuth2Adapter.close();
    }
}
