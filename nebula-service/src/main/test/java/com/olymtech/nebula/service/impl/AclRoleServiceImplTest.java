package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.AclRole;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.service.IAclRoleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;

/**
 * Created by WYQ on 2015/11/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.config.xml" })
@TransactionConfiguration(defaultRollback = false)
public class AclRoleServiceImplTest {

    @Resource
    private IAclRoleService aclRoleService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testInsertAclRole() throws Exception {
        AclRole aclRole = new AclRole();
        aclRole.setRoleName("bbb");
        aclRoleService.insertAclRole(aclRole);
    }

    @Test
    public void testDeleteAclRoleById() throws Exception {
        aclRoleService.deleteAclRoleById(1);
    }

    @Test
    public void testUpdateAclRole() throws Exception {
        AclRole aclRole = new AclRole();
        aclRole.setId(2);
        aclRole.setRoleName("ddd");
        aclRoleService.updateAclRole(aclRole);
    }

    @Test
    public void testGetPageInfoAclRole() throws Exception {
        DataTablePage dataTablePage = new DataTablePage();
        dataTablePage.setPageNum(2);
        dataTablePage.setPageSize(3);
        PageInfo pageInfo = aclRoleService.getPageInfoAclRole(dataTablePage);
        System.out.println(pageInfo);
    }
}