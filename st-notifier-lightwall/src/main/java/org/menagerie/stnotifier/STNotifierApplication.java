package org.menagerie.stnotifier;

import org.menagerie.stnotifier.config.STNotifierConfig;
import org.menagerie.stnotifier.config.STNotifierConfigImpl;
import org.menagerie.stnotifier.console.RenderTarget;
import org.menagerie.stnotifier.console.SwingRenderTarget;
import org.menagerie.stnotifier.console.SwingTerminalBean;
import org.menagerie.stnotifier.console.SwingTerminalBeanImpl;
import org.menagerie.stnotifier.gpio.GPIOControllerFactory;
import org.menagerie.stnotifier.gpio.GPIOControllerFactoryImpl;
import org.menagerie.stnotifier.gpio.GPIORenderTargetImpl;
import org.menagerie.stnotifier.i2c.I2CDeviceFactory;
import org.menagerie.stnotifier.i2c.I2CDeviceFactoryImpl;
import org.menagerie.stnotifier.i2c.I2CRenderTargetImpl;
import org.menagerie.stnotifier.renderer.MessageRenderer;
import org.menagerie.stnotifier.renderer.MessageRendererImpl;
import org.menagerie.stnotifier.renderer.Sleeper;
import org.menagerie.stnotifier.renderer.SleeperImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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

    @Profile("i2c")
    @Bean(initMethod = "init") RenderTarget i2cRenderTarget()
    {
        return new I2CRenderTargetImpl();
    }

    @Profile("i2c")
    @Bean(initMethod = "init")
    I2CDeviceFactory i2CDeviceFactory()
    {
        return new I2CDeviceFactoryImpl();
    }

    @Profile("gpio")
    @Bean(initMethod = "init") RenderTarget gpioRenderTarget()
    {
        return new GPIORenderTargetImpl();
    }

    @Bean GPIOControllerFactory gpioControllerFactory()
    {
        return new GPIOControllerFactoryImpl();
    }

    @Profile("mocked")
    @Bean(initMethod = "init") RenderTarget swingRenderTarget()
    {
        return new SwingRenderTarget();
    }

    @Bean SwingTerminalBean swingTerminalBean()
    {
        return new SwingTerminalBeanImpl();
    }

    @Bean(initMethod = "init")
    MessageRenderer messageRenderer()
    {
        return new MessageRendererImpl();
    }

    @Bean STNotifierConfig stNotifierConfig()
    {
        return new STNotifierConfigImpl();
    }

    @Bean
    Sleeper sleeper()
    {
        return new SleeperImpl();
    }
}
