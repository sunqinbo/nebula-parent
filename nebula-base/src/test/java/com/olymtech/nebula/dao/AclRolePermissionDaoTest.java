/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.AclRolePermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.List;

/**
 * @author taoshanchang 15/11/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class AclRolePermissionDaoTest {

    @Autowired
    private IAclRolePermissionDao aclRolePermissionDao;


    @Test
    public void getUserRoleTest(){
        List<AclRolePermission> result= aclRolePermissionDao.selectByRoleId(1);
        System.out.println(result.size());
        for (AclRolePermission r : result) {
            System.out.println(r.getAclPermission().getPermission());
        }

    }
}
