package org.menagerie.stnotifier.video.listener;

import com.esotericsoftware.minlog.Log;
import com.google.api.services.youtube.model.Video;
import org.menagerie.stnotifier.binding.VideoStartMessage;
import org.menagerie.stnotifier.video.gphoto2.Gphoto2CLI;
import org.menagerie.stnotifier.video.messaging.twilio.TwilioMessageSender;
import org.menagerie.stnotifier.video.youtube.YoutubeUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/18/16, 6:53 PM
 */
@Component
@EnableBinding(Processor.class)
public class VideoStartEventListener
{
    private static final Logger log = LoggerFactory.getLogger(VideoStartEventListener.class);

    private Gphoto2CLI gphoto2CLI;

    private YoutubeUploader youtubeUploader;

    private TwilioMessageSender twilioMessageSender;

    @StreamListener(value = Processor.INPUT)
    public void videoListener(VideoStartMessage videoStartMessage)
    {
        log.info("Received message: " + videoStartMessage.getStMessage().getBody());
        handleVideoStartMessage(videoStartMessage);
    }

    @Async
    private void handleVideoStartMessage(VideoStartMessage videoStartMessage)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHmsS");
        File f = new File("video-" + format.format(Calendar.getInstance().getTime()) + ".mov");
        gphoto2CLI.captureMovie(videoStartMessage.getDuration(), f.getAbsolutePath());
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
    }

    @Autowired
    public void setGphoto2CLI(Gphoto2CLI gphoto2CLI)
    {
        this.gphoto2CLI = gphoto2CLI;
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
