/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.ActionChain;
import com.olymtech.nebula.core.action.Dispatcher;
import com.olymtech.nebula.core.utils.SpringUtils;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.config.xml" })
@TransactionConfiguration(defaultRollback = false)
public class ActoinTest {


    @org.junit.Before
    public void init() throws Exception {


    }

    @Test
    public void actionTest(){
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(SpringUtils.getBean(PublishRelationAction.class));

        Dispatcher dispatcher = new Dispatcher(chain);

        try {
            //执行拦截器
            NebulaPublishEvent event=new NebulaPublishEvent();
            event.setPublishProductName("yjt2014");
            event.setId(9);
            event.setPublishEnv("test");
            dispatcher.doDispatch(event);
            System.out.println("Success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
