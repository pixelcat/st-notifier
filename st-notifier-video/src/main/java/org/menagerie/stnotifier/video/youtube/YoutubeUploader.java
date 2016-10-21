package org.menagerie.stnotifier.video.youtube;

import com.google.api.services.youtube.model.Video;

import java.io.IOException;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/19/16, 9:33 PM
 */
public interface YoutubeUploader
{
    @SuppressWarnings("unused") Video uploadVideo(String filename, String message) throws IOException;
}
