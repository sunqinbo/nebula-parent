package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaPublishSlbDao;
import com.olymtech.nebula.entity.NebulaPublishSlb;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

/**
 * Created by wangyiqiang on 16/1/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class NebulaPublishSlbDaoImplTest {
    @Resource
    private INebulaPublishSlbDao iNebulaPublishSlbDao;

    @Test
    public void testDeleteByEventId() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {
        NebulaPublishSlb nebulaPublishSlb = new NebulaPublishSlb();
        nebulaPublishSlb.setLoadBalancerAddress("hahaha");
        nebulaPublishSlb.setPublishEventId(2);
        nebulaPublishSlb.setLoadBalancerId("2");
        nebulaPublishSlb.setPublishModuleId(2);
        nebulaPublishSlb.setLoadBalancerStatus("hehehee");
        nebulaPublishSlb.setLoadBalancerName("eeee");
        iNebulaPublishSlbDao.insert(nebulaPublishSlb);
    }

    @Test
    public void testSelectById() throws Exception {
        NebulaPublishSlb nebulaPublishSlb = iNebulaPublishSlbDao.selectById(1);
    }

    @Test
    public void testUpdate() throws Exception {
        NebulaPublishSlb nebulaPublishSlb = new NebulaPublishSlb();
        nebulaPublishSlb.setLoadBalancerAddress("enenen");
        nebulaPublishSlb.setId(2);
        nebulaPublishSlb.setPublishEventId(2);
        nebulaPublishSlb.setLoadBalancerId("2");
        nebulaPublishSlb.setPublishModuleId(2);
        nebulaPublishSlb.setLoadBalancerStatus("eenenenen");
        nebulaPublishSlb.setLoadBalancerName("eeeneneneneee");
        iNebulaPublishSlbDao.update(nebulaPublishSlb);
    }

    @Test
    public void testUpdateByIdSelective() throws Exception {
        NebulaPublishSlb nebulaPublishSlb = new NebulaPublishSlb();
        nebulaPublishSlb.setLoadBalancerAddress("meiyou");
        nebulaPublishSlb.setId(2);
        nebulaPublishSlb.setPublishEventId(3);
        nebulaPublishSlb.setLoadBalancerId("2");
        nebulaPublishSlb.setPublishModuleId(3);
        nebulaPublishSlb.setLoadBalancerName("dddd");
        iNebulaPublishSlbDao.updateByIdSelective(nebulaPublishSlb);
    }

    @Test
    public void testDeleteById() throws Exception {
        iNebulaPublishSlbDao.deleteById(2);
    }

    @Test
    public void testSelectAllPaging() throws Exception {

    }

    @Test
    public void testSelectAll() throws Exception {

    }

    @Test
    public void testSelectCount() throws Exception {

    }


}