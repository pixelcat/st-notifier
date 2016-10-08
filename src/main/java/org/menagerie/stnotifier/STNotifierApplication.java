package org.menagerie.stnotifier;

import org.menagerie.stnotifier.console.SwingRenderTarget;
import org.menagerie.stnotifier.renderer.SwingMessageRenderer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 9/28/16, 9:38 PM
 */
@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories
@EnableMongoAuditing
@EnableScheduling
@ComponentScan(basePackages = {"org.menagerie.stnotifier.controller", "org.menagerie.stnotifier.tasks"})
public class STNotifierApplication
{
    @Bean(initMethod = "init") SwingRenderTarget swingRenderTarget()
    {
        return new SwingRenderTarget();
    }

    @Bean SwingMessageRenderer cursesMessageRenderer()
    {
        return new SwingMessageRenderer();
    }

    public static void main(String[] args) throws Exception
    {
        new SpringApplicationBuilder(STNotifierApplication.class).headless(false).run(args);
    }
}
