/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaUserInfoDao;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author taoshanchang 15/11/9
 */

@Service
public class UserServiceImpl implements IUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private INebulaUserInfoDao nebulaUserInfoDao;

    @Override
    public NebulaUserInfo findByUsername(String username) {
        NebulaUserInfo nebulaUserInfo = nebulaUserInfoDao.selectByUsername(username);
        return nebulaUserInfo;
    }

    @Override
    public Set<String> findRoles(String username) {
        Set<String> roles = new HashSet<String>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    @Override
    public Set<String> findPermissions(String username) {
        Set<String> permissions = new HashSet<String>();
        permissions.add("add");
        permissions.add("delete");
        return permissions;
    }
}
