package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.entity.NebulaUserInfo;
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
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(111);
        roleIds.add(122);
        roleIds.add(133);
        userInfo.setEmail("11-20-15:29");
        userInfo.setEmpId(2);
        userService.insertNebulaUserInfo(userInfo, roleIds);
    }

    @Test
    public void testDeleteNebulaUserInfo() throws Exception {
        userService.deleteNebulaUserInfo(3);
    }

    @Test
    public void testUpdateNebulaUserInfo() throws Exception {
        NebulaUserInfo userInfo = new NebulaUserInfo();
        List<Integer> roleIds = new ArrayList<>();
        userInfo.setEmpId(6);
        userInfo.setId(6);
        userInfo.setEmail("bbbbbbbbb");

        roleIds.add(13);
        roleIds.add(14);
        roleIds.add(22);

        userService.updateNebulaUserInfo(userInfo, roleIds);
    }

    @Test
    public void testGetPageInfoAclUser() throws Exception {
        userService.getAclUserWithRolesByEmpId(2);
        System.out.println(userService.getAclUserWithRolesByEmpId(2));
    }
}