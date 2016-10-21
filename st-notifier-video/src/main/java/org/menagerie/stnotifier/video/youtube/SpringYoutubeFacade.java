package org.menagerie.stnotifier.video.youtube;

import com.google.api.services.youtube.YouTube;

import java.io.IOException;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/19/16, 9:55 PM
 */
public interface SpringYoutubeFacade
{
    YouTube.Videos.Insert createVideoInsert(String filename, String message) throws IOException;
}
