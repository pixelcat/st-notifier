package org.menagerie.stnotifier;

import org.menagerie.stnotifier.console.RenderTarget;
import org.menagerie.stnotifier.console.SwingRenderTarget;
import org.menagerie.stnotifier.console.SwingTerminalBean;
import org.menagerie.stnotifier.console.SwingTerminalBeanImpl;
import org.menagerie.stnotifier.renderer.MessageRenderer;
import org.menagerie.stnotifier.renderer.MessageRendererImpl;
import org.menagerie.stnotifier.renderer.Sleeper;
import org.menagerie.stnotifier.renderer.SleeperImpl;
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
@ComponentScan(basePackages = {
        "org.menagerie.stnotifier.controller",
        "org.menagerie.stnotifier.tasks",
        "org.menagerie.stnotifier.repository",
        "org.menagerie.stnotifier.renderer"
})
public class STNotifierApplication
{
    public static void main(String[] args) throws Exception
    {
        new SpringApplicationBuilder(STNotifierApplication.class).headless(false).run(args);
    }

    @Bean(initMethod = "init") RenderTarget swingRenderTarget()
    {
        return new SwingRenderTarget();
    }

    @Bean MessageRenderer messageRenderer()
    {
        return new MessageRendererImpl();
    }

    @Bean SwingTerminalBean swingTerminalBean()
    {
        return new SwingTerminalBeanImpl();
    }

    @Bean
    Sleeper sleeper()
    {
        return new SleeperImpl();
    }
}
