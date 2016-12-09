package org.menagerie.stnotifier.video.listener;

import org.menagerie.stnotifier.binding.VideoStartMessage;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.util.concurrent.Future;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 12/9/16, 12:23 PM
 */
public interface AsyncVideoUploader
{
    @Async Future<Boolean> uploadVideo(VideoStartMessage videoStartMessage, File f);
}
