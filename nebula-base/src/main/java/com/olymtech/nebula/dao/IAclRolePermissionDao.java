/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.AclRolePermission;

import java.util.List;

/**
 * Created by Gavin on 2015-11-10 00:51.
 */
public interface IAclRolePermissionDao extends IBaseDao<AclRolePermission, Integer> {

    public List<AclRolePermission> selectByRoleId(Integer roleId);

    public void deleteByRoleId(Integer roleId);
}
