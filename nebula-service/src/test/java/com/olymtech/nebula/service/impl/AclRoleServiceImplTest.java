package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.AclRole;
import com.olymtech.nebula.entity.AclRolePermission;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.service.IAclRoleService;
import org.apache.log4j.pattern.LineSeparatorPatternConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYQ on 2015/11/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
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
        List<Integer> permissionIds = new ArrayList<>();
        permissionIds.add(111);
        permissionIds.add(121);
        permissionIds.add(131);
        aclRole.setId(10);

        aclRoleService.insertAclRole(aclRole, permissionIds);

    }

    @Test
    public void testDeleteAclRoleById() throws Exception {
        aclRoleService.deleteAclRoleById(1);
    }

    @Test
    public void testUpdateAclRole() throws Exception {
        AclRole aclRole = new AclRole();
        List<Integer> permissionIds = new ArrayList<>();
        aclRole.setRoleName("10:09");
        aclRole.setId(9);
        permissionIds.add(1);
        permissionIds.add(2);
        permissionIds.add(7);

        aclRoleService.updateAclRole(aclRole, permissionIds);
    }

    @Test
    public void testGetPageInfoAclRole() throws Exception {
        DataTablePage dataTablePage = new DataTablePage();
        dataTablePage.setPageNum(2);
        dataTablePage.setPageSize(3);
        PageInfo pageInfo = aclRoleService.getPageInfoAclRole(dataTablePage);
        System.out.println(pageInfo);
    }

    @Test
    public void testGetAclRoleWithPermissionsByRoleId() throws Exception {
        aclRoleService.getAclRoleWithPermissionsByRoleId(1);
        System.out.println(aclRoleService.getAclRoleWithPermissionsByRoleId(10));
    }

}