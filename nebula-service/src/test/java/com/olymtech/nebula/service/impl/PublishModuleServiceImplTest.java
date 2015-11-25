package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.service.INebulaPublishModuleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

/**
 * Created by WYQ on 2015/11/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class PublishModuleServiceImplTest {

    @Resource
    INebulaPublishModuleService publishModuleService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSelectByEventId() throws Exception {
        publishModuleService.selectByEventId(6);
    }

    @Test
    public void testDeleteByEventId() throws Exception {

    }
}