package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.AclPermission;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.service.IAclPermissionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

/**
 * Created by WYQ on 2015/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.config.xml" })
@TransactionConfiguration(defaultRollback = false)
public class AclPermissionServiceImplTest {

    @Resource
    private IAclPermissionService permissionService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInsertAclPermission() throws Exception {
        AclPermission permission = new AclPermission();
        permission.setIsEnable(1);
        permission.setPermission("permission");
        permission.setPermissionCname("aaa");
        permission.setPermissionDesc("bbb");
        permission.setPermissionName("ccc");
        permission.setPid(444);
        permission.setPermissionType("ffff");
        permission.setUrl("eee");
        permission.setPids("fff");
        permissionService.insertAclPermission(permission);
    }

    @Test
    public void testDeleteAclPermissionById() throws Exception {
        permissionService.deleteAclPermissionById(33);
    }

    @Test
    public void testUpdateAclPermission() throws Exception {
        AclPermission permission = new AclPermission();
        permission.setId(1);
        permission.setPid(888);
        permissionService.updateAclPermission(permission);
    }

    @Test
    public void testGetPageInfoAclPermission() throws Exception {
        DataTablePage dataTablePage = new DataTablePage();
        dataTablePage.setPageNum(2);
        dataTablePage.setPageSize(3);
        PageInfo pageInfo =  permissionService.getPageInfoAclPermission(dataTablePage);
        System.out.println(pageInfo);
    }
}