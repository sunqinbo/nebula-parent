package com.olymtech.nebula.service.starry.impl;

import com.aliyuncs.cdn.model.v20141111.DescribeRefreshTasksResponse;
import com.olymtech.nebula.service.starry.IStarryCdnApi;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2016-01-06 19:32.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class StarryCdnApiImplTest {

    @Resource
    private IStarryCdnApi starryCdnApi;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDescribeRefreshTasksResponse() throws Exception {
        DescribeRefreshTasksResponse describeRefreshTasksResponse = starryCdnApi.describeRefreshTasks("olymtech@aliyun.com", "cn-hangzhou");
        System.out.println(describeRefreshTasksResponse);
    }
}