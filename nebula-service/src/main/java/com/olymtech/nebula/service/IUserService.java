/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.NebulaUserInfo;

import java.util.Set;

/**
 * Created by taoshanchang on 15/11/9.
 */
public interface IUserService {


    public NebulaUserInfo findByUsername(String username);

    Set<String> findRoles(String username);

    Set<String> findPermissions(String username);

    public int insertNebulaUserInfo(NebulaUserInfo nebulaUserInfo);

    public void deleteNebulaUserInfo(Integer id);

    public void updateNebulaUserInfo(NebulaUserInfo nebulaUserInfo);

    public PageInfo getPageInfoAclUser(DataTablePage dataTablePage);
}
