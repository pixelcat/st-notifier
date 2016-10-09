package org.menagerie.stnotifier.controller;

import org.joda.time.DateTime;
import org.menagerie.stnotifier.model.STMessage;
import org.menagerie.stnotifier.repository.STMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/8/16, 11:28 AM
 */
@Component
@Controller
public class MessageController
{
    private static Logger log = LoggerFactory.getLogger(MessageController.class);

    private STMessageRepository stMessageRepository;

    @RequestMapping(path = "/message", method = RequestMethod.POST)
    @ResponseBody
    public STMessage receiveMessage(
            @RequestParam("AccountSid") String accountSid,
            @RequestParam("ApiVersion") String apiVersion,
            @RequestParam(value = "Body") String body,
            @RequestParam(value = "From") String from,
            @RequestParam(value = "FromCity", required = false) String fromCity,
            @RequestParam(value = "FromCountry", required = false) String fromCountry,
            @RequestParam(value = "FromState", required = false) String fromState,
            @RequestParam(value = "FromZip", required = false) String fromZip,
            @RequestParam(value = "MessageSid", required = false) String messageSid,
            @RequestParam(value = "MessagingServiceSid", required = false) String messagingServiceSid,
            @RequestParam(value = "NumMedia", required = false) String numMedia,
            @RequestParam(value = "NumSegments") String numSegments,
            @RequestParam(value = "SmsMessageSid") String smsMessageSid,
            @RequestParam(value = "SmsSid", required = false) String smsSid,
            @RequestParam(value = "SmsStatus", required = false) String smsStatus,
            @RequestParam(value = "To") String to,
            @RequestParam(value = "ToCity", required = false) String toCity,
            @RequestParam(value = "ToState", required = false) String toState,
            @RequestParam(value = "ToZip", required = false) String toZip)
    {

        log.info("received message");
        STMessage message = new STMessage();
        message.setAccountSid(accountSid);
        message.setApiVersion(apiVersion);
        message.setBody(body);
        message.setFrom(from);
        message.setFromCity(fromCity);
        message.setFromCountry(fromCountry);
        message.setFromState(fromState);
        message.setFromZip(fromZip);
        message.setMessageSid(messageSid);
        message.setMessagingServiceSid(messagingServiceSid);
        message.setNumMedia(numMedia);
        message.setNumSegments(numSegments);

        DateTime receivedDate = new DateTime();

        message.setReceivedDate(receivedDate);
        message.setSmsMessageSid(smsMessageSid);
        message.setSmsSid(smsSid);
        message.setSmsStatus(smsStatus);
        message.setTo(to);
        message.setToCity(toCity);
        message.setToState(toState);
        message.setToZip(toZip);

        stMessageRepository.insert(message);

        return message;
    }

    @Autowired
    public void setStMessageRepository(@SuppressWarnings("SpringJavaAutowiringInspection") STMessageRepository stMessageRepository)
    {
        this.stMessageRepository = stMessageRepository;
    }
}
