package org.menagerie.stnotifier.config;

import org.menagerie.stnotifier.model.STConfig;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 11/13/16, 3:26 PM
 */
public interface STNotifierConfig
{
    STConfig getConfig();
    STConfig saveConfig(STConfig config);
}
