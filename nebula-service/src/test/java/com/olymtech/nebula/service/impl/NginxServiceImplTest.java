package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.entity.NginxServer;
import com.olymtech.nebula.service.INginxService;
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
 * Created by Gavin on 2016-06-23 17:40.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class NginxServiceImplTest {

    @Autowired
    private INginxService nginxService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetMasterNginxServers() throws Exception {
        List<NginxServer> nginxServerList = nginxService.getStageNginxServers();

    }
}