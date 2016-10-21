package org.menagerie.stnotifier.video.test;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatistics;
import com.google.api.services.youtube.model.VideoStatus;
import io.codearte.jfairy.Fairy;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.menagerie.stnotifier.video.youtube.SpringYoutubeFacade;
import org.menagerie.stnotifier.video.youtube.YoutubeUploaderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/16/16, 5:15 PM
 */
@RunWith(SpringRunner.class)
public class YoutubeUploaderTest
{
    Mockery mockery;

    @Before
    public void setUp()
    {
        mockery = new Mockery();
        mockery.setImposteriser(ClassImposteriser.INSTANCE);
    }

    @Test
    public void testUpload() throws IOException
    {
        YoutubeUploaderImpl youtubeUploader = new YoutubeUploaderImpl();
        SpringYoutubeFacade facade = mockery.mock(SpringYoutubeFacade.class);
        youtubeUploader.setYoutubeFacade(facade);

        String filename = "/Users/acs/develop/st-notifier/st-video/testfile-lqjwxsokermjuoj.mov";
        String message = "test message";
        YouTube.Videos.Insert insert = mockery.mock(YouTube.Videos.Insert.class);

        Video expectedReturnVideo = new Video();
        VideoSnippet testSnippet = new VideoSnippet();
        expectedReturnVideo.setSnippet(testSnippet);
        VideoStatus videoStatus = new VideoStatus();
        expectedReturnVideo.setStatus(videoStatus);

        expectedReturnVideo.setStatistics(new VideoStatistics());
        mockery.checking(new Expectations()
        {
            {
                one(facade).createVideoInsert(filename, message);
                will(returnValue(insert));

                one(insert).execute();
                will(returnValue(expectedReturnVideo));
            }
        });
        Video video = youtubeUploader.uploadVideo(filename, message);

        assertNotNull(video);
        assertSame(video, expectedReturnVideo);

        mockery.assertIsSatisfied();

    }
}
