package org.menagerie.stnotifier.video.listener;

import com.esotericsoftware.minlog.Log;
import com.google.api.services.youtube.model.Video;
import org.menagerie.stnotifier.binding.VideoStartMessage;
import org.menagerie.stnotifier.video.messaging.twilio.TwilioMessageSender;
import org.menagerie.stnotifier.video.youtube.YoutubeUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 12/9/16, 12:22 PM
 */
public class AsyncVideoUploaderImpl implements AsyncVideoUploader
{
    private YoutubeUploader youtubeUploader;

    private TwilioMessageSender twilioMessageSender;

    @Override @Async
    public Future<Boolean> uploadVideo(VideoStartMessage videoStartMessage, File f)
    {
        try {
            if (f.exists()) {
                Video video = youtubeUploader.uploadVideo(f.getAbsolutePath(), videoStartMessage.getStMessage().getBody());
                String videoId = video.getId();
                String videoUrl = "https://www.youtube.com/watch?v=" + videoId;
                twilioMessageSender.sendResponse(videoStartMessage.getStMessage(), videoUrl);
            }
            else {
                Log.error("File " + f.getAbsolutePath() + " was not created. Skipping upload.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(true);
    }

    @Autowired
    public void setYoutubeUploader(YoutubeUploader youtubeUploader)
    {
        this.youtubeUploader = youtubeUploader;
    }

    @Autowired
    public void setTwilioMessageSender(TwilioMessageSender twilioMessageSender)
    {
        this.twilioMessageSender = twilioMessageSender;
    }
}
