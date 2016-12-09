package org.menagerie.stnotifier.video;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.menagerie.stnotifier.video.controller.PluggableVerificationCodeReceiver;
import org.menagerie.stnotifier.video.controller.PluggableVerificationCodeReceiverImpl;
import org.menagerie.stnotifier.video.gphoto2.Gphoto2CLI;
import org.menagerie.stnotifier.video.gphoto2.Gphoto2CLIImpl;
import org.menagerie.stnotifier.video.gphoto2.MockGphoto2CLIImpl;
import org.menagerie.stnotifier.video.listener.AsyncVideoUploader;
import org.menagerie.stnotifier.video.listener.AsyncVideoUploaderImpl;
import org.menagerie.stnotifier.video.messaging.twilio.TwilioMessageSender;
import org.menagerie.stnotifier.video.messaging.twilio.TwilioMessageSenderImpl;
import org.menagerie.stnotifier.video.youtube.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/16/16, 5:09 PM
 */
@SpringBootApplication
@EnableAsync
public class STVideoApplication extends AsyncConfigurerSupport
{
    private static Logger log = LoggerFactory.getLogger(STVideoApplication.class);

    public static void main(String[] args)
    {
        SpringApplication.run(STVideoApplication.class, args);
    }

    @Bean
    YoutubeUploader youtubeUploader()
    {
        return new YoutubeUploaderImpl();
    }

    @Bean SpringYoutubeFacade springYoutubeFacade() {
        return new SpringYoutubeFacadeImpl();
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

    @Bean(initMethod = "init")
    TwilioMessageSender twilioMessageSender()
    {
        return new TwilioMessageSenderImpl();
    }

    @Profile("camera")
    @Bean Gphoto2CLI gphoto2CLI()
    {
        return new Gphoto2CLIImpl();
    }

    @Profile("mocked")
    @Bean MockGphoto2CLIImpl mockGphoto2CLI() { return new MockGphoto2CLIImpl(); }

    @Bean
    HttpTransport httpTransport() {
        return new ApacheHttpTransport();
    }

    @Bean
    JsonFactory jsonFactory() {
      return new JacksonFactory();
    }

    @Bean AsyncVideoUploader asyncVideoUploader() {
        return new AsyncVideoUploaderImpl();
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("YoutubeUploader-");
        executor.initialize();
        return executor;
    }
}
