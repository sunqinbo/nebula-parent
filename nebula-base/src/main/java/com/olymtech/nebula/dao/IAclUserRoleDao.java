/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.AclRolePermission;
import com.olymtech.nebula.entity.AclUserRole;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.List;

/**
 * Created by Gavin on 2015-11-10 00:53.
 */
public interface IAclUserRoleDao extends IBaseDao<AclUserRole,Integer>{

    public void deleteByEmpId(Integer empId);

    public List<AclUserRole> selectByEmpId(Integer empId);
}
