package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.service.IPublishEventService;
import com.olymtech.nebula.service.IQuarryApiService;
import com.olymtech.nebula.service.IUserService;
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
 * Created by Gavin on 2016-05-27 14:56.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class QuarryApiServiceImplTest {

    @Autowired
    private IQuarryApiService quarryApiService;
    @Autowired
    private IPublishEventService publishEventService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNotifyDeployEndToQuarry() throws Exception {
        NebulaPublishEvent event = publishEventService.selectById(393);
        quarryApiService.notifyDeployEndToQuarry(event);
    }
}