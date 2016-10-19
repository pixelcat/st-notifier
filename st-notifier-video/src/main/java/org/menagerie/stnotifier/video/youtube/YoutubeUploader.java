package org.menagerie.stnotifier.video.youtube;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/16/16, 3:53 PM
 */
@Component
public class YoutubeUploader
{
    private static Logger log = LoggerFactory.getLogger(YoutubeUploader.class);

    private OAuth2Adapter oAuth2Adapter;

    @SuppressWarnings("unused") public Video uploadVideo(String filename, String message) throws IOException
    {
        JsonFactory jsonFactory = new JacksonFactory();
        HttpTransport httpTransport = new ApacheHttpTransport();
        List<String> scopes = new ArrayList<>();
        scopes.add("https://www.googleapis.com/auth/youtube.upload");

        Credential credential = oAuth2Adapter.authorize(scopes, "uploadvideo");
        YouTube youTube = new YouTube.Builder(httpTransport, jsonFactory, credential).setApplicationName("stranger-things-notifier").build();
        Video video = new Video();
        VideoStatus videoStatus = new VideoStatus();
        videoStatus.setPrivacyStatus("private");

        video.setStatus(videoStatus);

        VideoSnippet videoSnippet = new VideoSnippet();
        videoSnippet.setTitle("Menagerie Stranger Things Message Wall: " + message);

        List<String> tags = new ArrayList<>();
        tags.add("menagerie");
        tags.add("stranger things");
        tags.add("art");
        tags.add("message");
        tags.add("upsidedown");
        videoSnippet.setTags(tags);

        video.setSnippet(videoSnippet);

        InputStreamContent mediaContent = new InputStreamContent("video/*", new FileInputStream(new File(filename)));

        YouTube.Videos.Insert videoInsert = youTube.videos().insert("snippet,statistics,status", video, mediaContent);

        MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

        uploader.setDirectUploadEnabled(true);
        MediaHttpUploaderProgressListener progressListener = uploader1 -> {
            switch (uploader1.getUploadState()) {
                case INITIATION_STARTED:
                    log.info("Initiation Started");
                    break;
                case INITIATION_COMPLETE:
                    log.info("Initiation Completed");
                    break;
                case MEDIA_IN_PROGRESS:
                    log.info("Upload in progress");
                    log.info("Upload percentage: " + uploader1.getNumBytesUploaded());
                    break;
                case MEDIA_COMPLETE:
                    log.info("Upload Completed!");
                    break;
                case NOT_STARTED:
                    log.info("Upload Not Started!");
                    break;
            }
        };

        uploader.setProgressListener(progressListener);

        Video returnedVideo = videoInsert.execute();
        // Print data about the newly inserted video from the API response.
        log.debug("\n================== Returned Video ==================\n");
        log.debug("  - Id: " + returnedVideo.getId());
        log.debug("  - Title: " + returnedVideo.getSnippet().getTitle());
        log.debug("  - Tags: " + returnedVideo.getSnippet().getTags());
        log.debug("  - Privacy Status: " + returnedVideo.getStatus().getPrivacyStatus());
        log.debug("  - Video Count: " + returnedVideo.getStatistics().getViewCount());

        return returnedVideo;

    }

    @Autowired
    public void setoAuth2Adapter(OAuth2Adapter oAuth2Adapter)
    {
        this.oAuth2Adapter = oAuth2Adapter;
    }
}
