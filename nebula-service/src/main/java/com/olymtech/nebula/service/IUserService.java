/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NebulaUserInfo;

import java.util.Set;

/**
 * Created by taoshanchang on 15/11/9.
 */
public interface IUserService {


    public NebulaUserInfo findByUsername(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);
}
