/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.service.IPublishEventService;
import com.olymtech.nebula.service.impl.PublishEventServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * @author taoshanchang 15/11/4
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class CreateDirActionTest {


    @Autowired
    private IPublishEventService publishEventService;


    @Autowired
    private CreateDirAciton createDirAciton;

    @Before
    public void init() throws Exception {


    }

    @Test
    public void createDirActionTest(){

        System.out.println(publishEventService);


        try {
            createDirAciton.doAction(new NebulaPublishEvent());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
