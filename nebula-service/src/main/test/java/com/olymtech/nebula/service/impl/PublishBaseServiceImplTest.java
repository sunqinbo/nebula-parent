package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.olymtech.nebula.service.IPublishBaseService;
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
 * Created by Gavin on 2015-11-05 17:41.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.config.xml" })
@TransactionConfiguration(defaultRollback = false)
public class PublishBaseServiceImplTest {

    @Autowired
    private IPublishBaseService publishBaseService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSelectLastModuleKeyByPublishEvent() throws Exception {
        NebulaPublishEvent event = new NebulaPublishEvent();
        event.setPublishEnv("test");
        event.setPublishProductName("yjt2014");
        String moduleName = "ebooking";
        String moduleKey = publishBaseService.selectLastModuleKeyByPublishEvent(event,moduleName);
        System.out.println(moduleKey);
    }

    @Test
    public void testCheckoutHistoryDirKey() throws Exception {
        NebulaPublishEvent event = new NebulaPublishEvent();
        event.setPublishEnv("test");
        event.setPublishProductName("yjt2014");
        NebulaPublishModule module = new NebulaPublishModule();
        module.setPublishModuleName("ebooking");
        String key = publishBaseService.checkoutHistoryDirKey(event,module);
        System.out.println(key);
    }
}