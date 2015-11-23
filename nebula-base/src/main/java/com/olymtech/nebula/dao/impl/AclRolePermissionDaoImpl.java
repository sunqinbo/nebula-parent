/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.IAclRolePermissionDao;
import com.olymtech.nebula.entity.AclRolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gavin on 2015-11-10 00:52.
 */
@Repository
public class AclRolePermissionDaoImpl extends BaseDaoImpl<AclRolePermission,Integer> implements IAclRolePermissionDao {
    @Override
     public List<AclRolePermission> selectByRoleId(Integer roleId) {
        return getSqlSession().selectList(CLASS_NAME + "-Select-By-Role-Id", roleId);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {
        getSqlSession().delete(CLASS_NAME + "-Delete-By-Role-Id", roleId);
    }
}
