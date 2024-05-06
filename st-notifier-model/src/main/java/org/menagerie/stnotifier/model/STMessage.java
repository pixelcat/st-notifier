package org.menagerie.stnotifier.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.workhabit.mongobase.support.JodaDateTimeJsonSerializer;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 9/30/16, 10:00 PM
 */
@Document(collection = "stMessage")
public class STMessage
{
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private DateTime receivedDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = JodaDateTimeJsonSerializer.class)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private DateTime displayedDate;

    private boolean displayed = false;

    private String toCountry;
    private String toState;
    private String smsMessageSid;
    private String numMedia;
    private String toCity;
    private String fromZip;
    private String smsSid;
    private String fromState;
    private String smsStatus;
    private String fromCity;
    private String body;
    private String fromCountry;
    private String to;
    private String messagingServiceSid;
    private String toZip;
    private String numSegments;
    private String messageSid;
    private String accountSid;
    private String from;
    private String apiVersion;
    private boolean stickyToTop;
    private boolean blocked;


    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public DateTime getReceivedDate()
    {
        return receivedDate;
    }

    public void setReceivedDate(DateTime receivedDate)
    {
        this.receivedDate = receivedDate;
    }

    public DateTime getDisplayedDate()
    {
        return displayedDate;
    }

    public void setDisplayedDate(DateTime displayedDate)
    {
        this.displayedDate = displayedDate;
    }

    public boolean isDisplayed()
    {
        return displayed;
    }

    public void setDisplayed(boolean displayed)
    {
        this.displayed = displayed;
    }

    public String getToCountry()
    {
        return toCountry;
    }

    public void setToCountry(String toCountry)
    {
        this.toCountry = toCountry;
    }

    public String getToState()
    {
        return toState;
    }

    public void setToState(String toState)
    {
        this.toState = toState;
    }

    public String getSmsMessageSid()
    {
        return smsMessageSid;
    }

    public void setSmsMessageSid(String smsMessageSid)
    {
        this.smsMessageSid = smsMessageSid;
    }

    public String getNumMedia()
    {
        return numMedia;
    }

    public void setNumMedia(String numMedia)
    {
        this.numMedia = numMedia;
    }

    public String getToCity()
    {
        return toCity;
    }

    public void setToCity(String toCity)
    {
        this.toCity = toCity;
    }

    public String getFromZip()
    {
        return fromZip;
    }

    public void setFromZip(String fromZip)
    {
        this.fromZip = fromZip;
    }

    public String getSmsSid()
    {
        return smsSid;
    }

    public void setSmsSid(String smsSid)
    {
        this.smsSid = smsSid;
    }

    public String getFromState()
    {
        return fromState;
    }

    public void setFromState(String fromState)
    {
        this.fromState = fromState;
    }

    public String getSmsStatus()
    {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus)
    {
        this.smsStatus = smsStatus;
    }

    public String getFromCity()
    {
        return fromCity;
    }

    public void setFromCity(String fromCity)
    {
        this.fromCity = fromCity;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getFromCountry()
    {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry)
    {
        this.fromCountry = fromCountry;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }

    public String getMessagingServiceSid()
    {
        return messagingServiceSid;
    }

    public void setMessagingServiceSid(String messagingServiceSid)
    {
        this.messagingServiceSid = messagingServiceSid;
    }

    public String getToZip()
    {
        return toZip;
    }

    public void setToZip(String toZip)
    {
        this.toZip = toZip;
    }

    public String getNumSegments()
    {
        return numSegments;
    }

    public void setNumSegments(String numSegments)
    {
        this.numSegments = numSegments;
    }

    public String getMessageSid()
    {
        return messageSid;
    }

    public void setMessageSid(String messageSid)
    {
        this.messageSid = messageSid;
    }

    public String getAccountSid()
    {
        return accountSid;
    }

    public void setAccountSid(String accountSid)
    {
        this.accountSid = accountSid;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getApiVersion()
    {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion)
    {
        this.apiVersion = apiVersion;
    }

    public boolean isStickyToTop()
    {
        return stickyToTop;
    }

    public void setStickyToTop(boolean stickyToTop)
    {
        this.stickyToTop = stickyToTop;
    }

    public boolean isBlocked()
    {
        return blocked;
    }

    public void setBlocked(boolean blocked)
    {
        this.blocked = blocked;
    }
}
