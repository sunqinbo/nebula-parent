package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageHelper;
import com.olymtech.nebula.dao.INebulaPublishEventDao;
import com.olymtech.nebula.entity.FileChangeData;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.service.IPublishBaseService;
import com.olymtech.nebula.service.IPublishEventService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2015-11-12 09:46.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class PublishEventServiceImplTest {

    @Autowired
    private IPublishEventService publishEventService;

    @Autowired
    private INebulaPublishEventDao publishEventDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSelectWithChildByEventId() throws Exception {
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(6);
        System.out.println("Success");
    }

    @Test
    public void testSelectAllPagingWithUser() throws Exception {
        PageHelper.startPage(1, 10);
        List<NebulaPublishEvent> eventList = publishEventDao.selectAllPagingWithUser(new NebulaPublishEvent());
        System.out.println("sucess");
    }

    @Test
    public void testChangeListJsonStringToList() throws Exception {
        String a = "{\"/baidu/b.txt\": {\"time\": \"1970-01-01 08:00:00.000000000\", \"change\": \"-ddddddddddd\\n\", \"filename\": \"/baidu/b.txt\"}, \"/a.properties\": {\"time\": \"2016-03-09 09:30:18.000000000\", \"change\": \"+dddd:3333\\n\", \"filename\": \"/a.properties\"}, \"/arsenal.properties\": {\"time\": \"2016-03-09 09:29:27.000000000\", \"change\": \" ssss=3333\\n-eeee=3333\\n+cccc=4444\\n\", \"filename\": \"/arsenal.properties\"}}";
        System.out.println(a);
        List<FileChangeData> list = publishEventService.changeListJsonStringToList(a);
    }
}