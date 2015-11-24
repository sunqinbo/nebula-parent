/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.AclRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taoshanchang 15/11/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class AclRoleDaoTest {

    @Autowired
    private IAclRoleDao aclRoleDao;

    @Test
    public void getUserRoleTest(){
        ArrayList arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(4);
        List<AclRole> aclRoles = aclRoleDao.selectByIds(arrayList);
        for (AclRole aclRole : aclRoles) {
            System.out.println(aclRole.getRoleCname());
        }

    }
}
