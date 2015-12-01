package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.ActionChain;
import com.olymtech.nebula.core.action.Dispatcher;
import com.olymtech.nebula.core.utils.SpringUtils;
import com.olymtech.nebula.entity.NebulaPublishEvent;
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
 * Created by Gavin on 2015-12-01 10:57.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class UpdateSrcSvnActionTest {

    @Autowired
    private IPublishEventService publishEventService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDoAction() throws Exception {

        ActionChain chain = new ActionChain();
        chain.addAction(SpringUtils.getBean(UpdateSrcSvnAction.class));

        Dispatcher dispatcher = new Dispatcher(chain,null,null);

        try {
            NebulaPublishEvent event = publishEventService.selectWithChildByEventId(36);
            event.setPublishEnv("product");
            dispatcher.doDispatch(event);
            System.out.println("success");

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}