package org.menagerie.stnotifier.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.StringWriter;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/17/16, 11:19 PM
 */
@Controller
public class GoogleOAuth2Controller
{
    @Autowired
    PluggableVerificationCodeReceiver pluggableVerificationCodeReceiver;

    @RequestMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("error") String error) {
        StringWriter doc = new StringWriter();
        doc.write("<html>");
        doc.write("<head><title>OAuth 2.0 Authentication Token Received</title></head>");
        doc.write("<body>");
        doc.write("Received verification code. You may now close this window...");
        doc.write("</body>");
        doc.write("</HTML>");
        pluggableVerificationCodeReceiver.getLock().lock();

        try {
            pluggableVerificationCodeReceiver.setCode(code);
            pluggableVerificationCodeReceiver.setError(error);
        }
        finally {
            pluggableVerificationCodeReceiver.getLock().unlock();
        }
        return doc.toString();
    }
}
