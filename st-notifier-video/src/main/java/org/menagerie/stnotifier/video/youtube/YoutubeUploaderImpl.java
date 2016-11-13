package org.menagerie.stnotifier.video.youtube;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/16/16, 3:53 PM
 */
@Component
public class YoutubeUploaderImpl implements YoutubeUploader
{
    private static final Logger log = LoggerFactory.getLogger(YoutubeUploaderImpl.class);

    private SpringYoutubeFacade youtubeFacade;

    @Autowired
    public void setYoutubeFacade(@SuppressWarnings("SpringJavaAutowiringInspection") SpringYoutubeFacade youtubeFacade)
    {
        this.youtubeFacade = youtubeFacade;
    }

    @Override @SuppressWarnings("unused") public Video uploadVideo(String filename, String message) throws IOException
    {
        YouTube.Videos.Insert videoInsert = youtubeFacade.createVideoInsert(filename, message);

        Video returnedVideo = videoInsert.execute();
        // Print data about the newly inserted video from the API response.
        if (log.isDebugEnabled()) {
            log.debug("\n================== Returned Video ==================\n");
            log.debug("  - Id: " + returnedVideo.getId());
            log.debug("  - Title: " + returnedVideo.getSnippet().getTitle());
            log.debug("  - Tags: " + returnedVideo.getSnippet().getTags());
            log.debug("  - Privacy Status: " + returnedVideo.getStatus().getPrivacyStatus());
            log.debug("  - Video Count: " + returnedVideo.getStatistics().getViewCount());
        }
        return returnedVideo;

    }
}
