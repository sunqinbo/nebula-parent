package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.IPublishBaseService;
import com.olymtech.nebula.service.IPublishScheduleService;
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
 * Created by Gavin on 2015-11-06 16:03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.config.xml" })
@TransactionConfiguration(defaultRollback = false)
public class PublishScheduleServiceImplTest {

    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLogScheduleByAction() throws Exception {
        Integer id = publishScheduleService.logScheduleByAction(2, PublishAction.CLEAN_HISTORY_DIR, PublishActionGroup.SUCCESS_END, false, "error message");
        System.out.println(id);
    }

    @Test
    public void testSelectByEventId() throws Exception {

    }
}