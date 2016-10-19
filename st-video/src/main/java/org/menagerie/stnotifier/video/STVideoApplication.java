package org.menagerie.stnotifier.video;

import org.menagerie.stnotifier.video.controller.PluggableVerificationCodeReceiver;
import org.menagerie.stnotifier.video.controller.PluggableVerificationCodeReceiverImpl;
import org.menagerie.stnotifier.video.gphoto2.Gphoto2CLI;
import org.menagerie.stnotifier.video.gphoto2.Gphoto2CLIImpl;
import org.menagerie.stnotifier.video.youtube.OAuth2Adapter;
import org.menagerie.stnotifier.video.youtube.YoutubeUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/16/16, 5:09 PM
 */
@SpringBootApplication
public class STVideoApplication
{
    private static Logger log = LoggerFactory.getLogger(STVideoApplication.class);

    @Bean
    YoutubeUploader youtubeUploader()
    {
        return new YoutubeUploader();
    }

    @Bean
    OAuth2Adapter oAuth2Adapter()
    {
        return new OAuth2Adapter();
    }

    @Bean
    PluggableVerificationCodeReceiver springControllerVerificationCodeReceiver()
    {
        return new PluggableVerificationCodeReceiverImpl();
    }

    @Bean Gphoto2CLI gphoto2CLI()
    {
        return new Gphoto2CLIImpl();
    }

    public static void main(String[] args)
    {
        SpringApplication.run(STVideoApplication.class, args);
    }
}
