package org.menagerie.stnotifier.test;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Address;
import io.codearte.jfairy.producer.text.TextProducer;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.api.Invocation;
import org.jmock.lib.action.CustomAction;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.junit.Before;
import org.junit.Test;
import org.menagerie.stnotifier.controller.MessageController;
import org.menagerie.stnotifier.model.STMessage;
import org.menagerie.stnotifier.repository.STMessageRepository;

import java.util.Locale;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;

/**
 * Copyright 2016 - Aaron Stewart
 * Date: 10/8/16, 10:59 PM
 */
public class MessageControllerTest
{
    private Mockery mockery;

    @Before
    public void setUp()
    {
        mockery = new Mockery();

    }

    @Test
    public void testControllerReceiveMessage()
    {
        MessageController controller = new MessageController();
        STMessageRepository mockStMessageRepository = mockery.mock(STMessageRepository.class);
        controller.setStMessageRepository(mockStMessageRepository);
        Fairy fairy = Fairy.create(Locale.US);

        TextProducer textProducer = fairy.textProducer();
        String accountSid = textProducer.randomString(16);
        DateTimeFormatter dateFormat = new DateTimeFormatterBuilder().appendPattern("Y-m-d").toFormatter();
        String apiVersion = fairy.dateProducer().randomDateInThePast(3).toString(dateFormat);
        String body = textProducer.latinWord(3);
        String from = fairy.person().telephoneNumber();

        Address address = fairy.person().getAddress();

        String fromCity = address.getCity();
        String fromState = "CA";
        String fromZip = address.getPostalCode();
        String fromCountry = "US";
        String messageSid = textProducer.randomString(16);

        String messagingServiceSid = textProducer.randomString(16);
        String numMedia = "0";
        String numSegments = "1";
        String smsMessageSid = textProducer.randomString(16);
        String smsSid = textProducer.randomString(16);
        String smsStatus = "RECEIVED";
        String to = fairy.person().telephoneNumber();
        String toCity = address.getCity();
        String toState = "CA";
        String toZip = address.getPostalCode();


        mockery.checking(new Expectations()
        {
            {
                @SuppressWarnings("unchecked") Matcher<? extends STMessage> matcher = allOf(
                        isA(STMessage.class),
                        hasProperty("accountSid", equalTo(accountSid)),
                        hasProperty("apiVersion", equalTo(apiVersion)),
                        hasProperty("body", equalTo(body)),
                        hasProperty("from", equalTo(from)),
                        hasProperty("fromCity", equalTo(fromCity)),
                        hasProperty("fromCountry", equalTo(fromCountry)),
                        hasProperty("fromState", equalTo(fromState)),
                        hasProperty("fromZip", equalTo(fromZip)),
                        hasProperty("messageSid", equalTo(messageSid)),
                        hasProperty("messagingServiceSid", equalTo(messagingServiceSid)),
                        hasProperty("numMedia", equalTo(numMedia)),
                        hasProperty("numSegments", equalTo(numSegments)),
                        hasProperty("smsMessageSid", equalTo(smsMessageSid)),
                        hasProperty("smsSid", equalTo(smsSid)),
                        hasProperty("smsStatus", equalTo(smsStatus)),
                        hasProperty("to", equalTo(to)),
                        hasProperty("toCity", equalTo(toCity)),
                        hasProperty("toState", equalTo(toState)),
                        hasProperty("toZip", equalTo(toZip))

                );
                one(mockStMessageRepository).insert(with(matcher));

                will(returnLastActionParam());
            }
        });

        STMessage message = controller.receiveMessage(accountSid, apiVersion, body, from, fromCity, fromCountry, fromState, fromZip, messageSid, messagingServiceSid, numMedia, numSegments, smsMessageSid, smsSid, smsStatus, to, toCity, toState, toZip);
        assertNotNull(message);

        mockery.assertIsSatisfied();
    }

    private CustomAction returnLastActionParam()
    {
        return new CustomAction("Returns first arg")
        {
            @Override public Object invoke(Invocation invocation) throws Throwable
            {
                return invocation.getParameter(0);
            }
        };
    }
}
