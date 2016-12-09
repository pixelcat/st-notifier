package org.menagerie.stnotifier.video.listener;

import org.menagerie.stnotifier.binding.VideoStartMessage;
import org.menagerie.stnotifier.video.gphoto2.Gphoto2CLI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.stereotype.Component;

import java.io.File;
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

    private AsyncVideoUploader videoUploader;

    @StreamListener(value = Processor.INPUT)
    public void videoListener(VideoStartMessage videoStartMessage)
    {
        log.info("Received message: " + videoStartMessage.getStMessage().getBody());
        handleVideoStartMessage(videoStartMessage);
        log.info("Finished video processing.");
    }


    private void handleVideoStartMessage(VideoStartMessage videoStartMessage)
    {
        log.info("Starting video processing.");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHmsS");
        File f = new File("video-" + format.format(Calendar.getInstance().getTime()) + ".mov");
        gphoto2CLI.captureMovie(videoStartMessage.getDuration(), f.getAbsolutePath());
        videoUploader.uploadVideo(videoStartMessage, f);
        log.info("Video processing complete. Upload may still be in progress.");
    }


    @Autowired
    public void setGphoto2CLI(Gphoto2CLI gphoto2CLI)
    {
        this.gphoto2CLI = gphoto2CLI;
    }

    @Autowired
    public void setVideoUploader(AsyncVideoUploader videoUploader)
    {
        this.videoUploader = videoUploader;
    }
}
