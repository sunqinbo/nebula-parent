/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.AclRole;

import java.util.List;

/**
 * Created by Gavin on 2015-11-10 00:50.
 */
public interface IAclRoleDao extends IBaseDao<AclRole,Integer> {

    public List<AclRole> selectByIds(List<Integer> ids);

}
