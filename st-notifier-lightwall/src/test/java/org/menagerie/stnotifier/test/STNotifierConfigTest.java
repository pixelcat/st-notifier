package org.menagerie.stnotifier.test;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.BaseProducer;
import org.bson.types.ObjectId;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.menagerie.stnotifier.config.STNotifierConfigImpl;
import org.menagerie.stnotifier.model.STConfig;
import org.menagerie.stnotifier.repository.STConfigRepository;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertEquals;

/**
 * Copyright 2016 - Kenzi Stewart
 * Date: 11/15/16, 9:59 PM
 */
public class STNotifierConfigTest
{

    private Mockery mockery;
    private Fairy fairy;
    private BaseProducer baseProducer;
    private int defaultOffTime;
    private int defaultOnTime;
    private int defaultWaitBetweenMessages;
    private int defaultWaitEnd;
    private int defaultWaitSpace;
    private int defaultWaitStart;

    @Before
    public void setUp()
    {
        mockery = new Mockery();
        fairy = Fairy.create();

        baseProducer = fairy.baseProducer();

        defaultOffTime = baseProducer.randomInt(10000);
        defaultOnTime = baseProducer.randomInt(10000);
        defaultWaitBetweenMessages = baseProducer.randomInt(10000);
        defaultWaitEnd = baseProducer.randomInt(10000);
        defaultWaitSpace = baseProducer.randomInt(10000);
        defaultWaitStart = baseProducer.randomInt(10000);
    }

    @Test
    public void testDefaultConfig()
    {

        STConfigRepository mockStConfigRepository = mockery.mock(STConfigRepository.class);
        STNotifierConfigImpl config = new STNotifierConfigImpl();

        config.setDefaultOffTime(defaultOffTime);
        config.setDefaultOnTime(defaultOnTime);
        config.setDefaultWaitBetweenMessages(defaultWaitBetweenMessages);
        config.setDefaultWaitEnd(defaultWaitEnd);
        config.setDefaultWaitSpace(defaultWaitSpace);
        config.setDefaultWaitStart(defaultWaitStart);

        STConfig defaultConfig = new STConfig();

        defaultConfig.setName("master");
        ObjectId objectId = new ObjectId();
        defaultConfig.setId(objectId);
        defaultConfig.setWaitBetweenMessages(defaultWaitBetweenMessages);
        defaultConfig.setOffTime(defaultOffTime);
        defaultConfig.setOnTime(defaultOnTime);
        defaultConfig.setWaitEnd(defaultWaitEnd);
        defaultConfig.setWaitSpace(defaultWaitSpace);
        defaultConfig.setWaitStart(defaultWaitStart);

        mockery.checking(new Expectations()
        {
            {
                oneOf(mockStConfigRepository).findFirstByName("master");
                will(returnValue(null));

                oneOf(mockStConfigRepository).insert(with(isA(STConfig.class)));
                will(returnValue(defaultConfig));
            }
        });
        config.setStConfigRepository(mockStConfigRepository);

        STConfig returnedConfig = config.getConfig();

        assertEquals(defaultWaitEnd, returnedConfig.getWaitEnd());
        assertEquals(defaultWaitSpace, returnedConfig.getWaitSpace());
        assertEquals(defaultWaitStart, returnedConfig.getWaitStart());
        assertEquals(defaultOffTime, returnedConfig.getOffTime());
        assertEquals(defaultOnTime, returnedConfig.getOnTime());
        assertEquals(defaultWaitBetweenMessages, returnedConfig.getWaitBetweenMessages());

        assertEquals(objectId, defaultConfig.getId());

        mockery.assertIsSatisfied();
    }

    @Test
    public void testSaveConfig() {
        STConfigRepository mockStConfigRepository = mockery.mock(STConfigRepository.class);
        STNotifierConfigImpl config = new STNotifierConfigImpl();

        STConfig savedConfig = new STConfig();
        savedConfig.setId(new ObjectId());
        savedConfig.setName("master");
        savedConfig.setWaitStart(baseProducer.randomInt(10000));
        savedConfig.setWaitBetweenMessages(baseProducer.randomInt(10000));
        savedConfig.setWaitSpace(baseProducer.randomInt(10000));
        savedConfig.setOffTime(baseProducer.randomInt(10000));
        savedConfig.setOnTime(baseProducer.randomInt(10000));
        savedConfig.setWaitEnd(baseProducer.randomInt(10000));

        mockery.checking(new Expectations() {
            {
                oneOf(mockStConfigRepository).findFirstByName("master");
                will(returnValue(savedConfig));
            }
        });
        config.setStConfigRepository(mockStConfigRepository);

        STConfig testConfig = config.getConfig();

        assertEquals(testConfig.getId(), savedConfig.getId());
        assertEquals(testConfig.getWaitSpace(), savedConfig.getWaitSpace());
        assertEquals(testConfig.getWaitBetweenMessages(), savedConfig.getWaitBetweenMessages());
        assertEquals(testConfig.getWaitEnd(), savedConfig.getWaitEnd());
        assertEquals(testConfig.getWaitStart(), savedConfig.getWaitStart());
        assertEquals(testConfig.getOnTime(), savedConfig.getOnTime());
        assertEquals(testConfig.getOffTime(), savedConfig.getOffTime());

        mockery.assertIsSatisfied();

    }
}
