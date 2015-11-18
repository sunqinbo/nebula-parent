package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.NebulaUserInfo;
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
 * Created by WYQ on 2015/11/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.config.xml" })
@TransactionConfiguration(defaultRollback = false)
public class UserServiceImplTest {

    @Resource
    private UserServiceImpl userService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFindByUsername() throws Exception {

    }

    @Test
    public void testFindRoles() throws Exception {

    }

    @Test
    public void testFindPermissions() throws Exception {

    }

    @Test
    public void testInsertNebulaUserInfo() throws Exception {
        NebulaUserInfo userInfo = new NebulaUserInfo();
        userInfo.setEmail("364514921@qq.com");
        userInfo.setEmpId(88);
        userService.insertNebulaUserInfo(userInfo);
    }

    @Test
    public void testDeleteNebulaUserInfo() throws Exception {
        userService.deleteNebulaUserInfo(2);
    }

    @Test
    public void testUpdateNebulaUserInfo() throws Exception {
        NebulaUserInfo userInfo = new NebulaUserInfo();
        userInfo.setId(3);
        userInfo.setEmpId(88);
        userInfo.setEmail("1286849100@163.com");
        userService.updateNebulaUserInfo(userInfo);
    }

    @Test
    public void testGetPageInfoAclUser() throws Exception {
        DataTablePage dataTablePage = new DataTablePage();
        dataTablePage.setPageNum(2);
        dataTablePage.setPageSize(3);
        PageInfo pageInfo =  userService.getPageInfoAclUser(dataTablePage);
        System.out.println(pageInfo);
    }
}