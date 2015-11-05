package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.entity.NebulaPublishApp;
import com.olymtech.nebula.service.IPublishAppService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2015-11-05 19:49.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.config.xml" })
@TransactionConfiguration(defaultRollback = false)
public class PublishAppServiceImplTest {

    @Resource
    private IPublishAppService publishAppService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSelectByEventIdAndModuleId() throws Exception {

        List<NebulaPublishApp> nebulaPublishApps = publishAppService.selectByEventIdAndModuleId(1,23);
        System.out.println(nebulaPublishApps);
    }

}