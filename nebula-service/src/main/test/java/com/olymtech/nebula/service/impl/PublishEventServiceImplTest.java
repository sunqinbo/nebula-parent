package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.service.IPublishBaseService;
import com.olymtech.nebula.service.IPublishEventService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2015-11-12 09:46.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.config.xml" })
@TransactionConfiguration(defaultRollback = false)
public class PublishEventServiceImplTest {

    @Autowired
    private IPublishEventService publishEventService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSelectWithChildByEventId() throws Exception {
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(6);
        System.out.println("Success");
    }
}