/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.ActionChain;
import com.olymtech.nebula.core.action.Dispatcher;
import com.olymtech.nebula.core.utils.SpringUtils;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * @author taoshanchang 15/11/5
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class ActionChainTest {

    @Before
    public void init() throws Exception {

    }

    @Test
    public void actionChainTest(){

        ActionChain actionChain = new ActionChain( );

        actionChain.addAction(SpringUtils.getBean(CpEtcAction.class));
        actionChain.addAction(SpringUtils.getBean(CreateDirAciton.class));

        try {
            Dispatcher dispatcher = new Dispatcher(actionChain,null,null);
            dispatcher.doDispatch(new NebulaPublishEvent());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
