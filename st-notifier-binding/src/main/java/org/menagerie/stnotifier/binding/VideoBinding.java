package org.menagerie.stnotifier.binding;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/15/16, 4:46 PM
 */
public interface VideoBinding
{
    @Output("startVideo")
    MessageChannel startVideo();

    @Output("notifyVideo")
    MessageChannel notifyVideo();
}
