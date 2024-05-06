package org.menagerie.stnotifier.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.StringWriter;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 10/17/16, 11:19 PM
 */
@Controller
public class GoogleOAuth2Controller
{
    private PluggableVerificationCodeReceiver pluggableNestVerificationCodeReceiver;
    private PluggableVerificationCodeReceiver pluggableInsteonVerificationCodeReceiver;

    @RequestMapping("/callback/{classifier}")
    public String callback(@RequestParam("code") String code, @RequestParam("error") String error, @PathVariable String classifier)
    {
        StringWriter doc = new StringWriter();
        doc.write("<html>");
        doc.write("<head><title>OAuth 2.0 Authentication Token Received</title></head>");
        doc.write("<body>");
        doc.write("Received verification code. You may now close this window...");
        doc.write("</body>");
        doc.write("</HTML>");
        PluggableVerificationCodeReceiver receiver;
        if ("insteon".equals(classifier)) {
            receiver = this.pluggableInsteonVerificationCodeReceiver;
        }
        else {
            receiver = this.pluggableNestVerificationCodeReceiver;
        }
        receiver.getLock().lock();

        try {
            receiver.setCode(code);
            receiver.setError(error);
        } finally {
            receiver.getLock().unlock();
        }
        return doc.toString();
    }

    @Autowired
    public void setPluggableNestVerificationCodeReceiver(PluggableVerificationCodeReceiver pluggableNestVerificationCodeReceiver)
    {
        this.pluggableNestVerificationCodeReceiver = pluggableNestVerificationCodeReceiver;
    }

    @Autowired
    public void setPluggableInsteonVerificationCodeReceiver(PluggableVerificationCodeReceiver pluggableInsteonVerificationCodeReceiver)
    {
        this.pluggableInsteonVerificationCodeReceiver = pluggableInsteonVerificationCodeReceiver;
    }
}
