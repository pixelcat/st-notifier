package org.menagerie.stnotifier.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/9/16, 8:41 PM
 */
@XmlRootElement(name = "Response")
public class TwilioResponse
{
    @XmlElement(name = "Message", required = true)
    public String message;

    public TwilioResponse()
    {
        this("");
    }

    public TwilioResponse(String message)
    {
        this.message = message;
    }
}
