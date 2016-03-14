package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.entity.enums.LogAction;
import com.olymtech.nebula.service.IPublishEventLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

/**
 * Created by wangyiqiang on 16/3/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class PublishEventLogServiceImplTest {

    @Resource
    private IPublishEventLogService publishEventLogService;

    @Test
    public void testLogPublishAction() throws Exception {
        Integer result = publishEventLogService.logPublishAction(10, LogAction.ENTER_NEXT_PUBLISH, "test4", 11);
        System.out.println(result);
    }
}