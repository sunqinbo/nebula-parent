/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.IAclUserRoleDao;
import com.olymtech.nebula.entity.AclUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gavin on 2015-11-10 00:54.
 */
@Repository
public class AclUserRoleDaoImpl extends BaseDaoImpl<AclUserRole,Integer> implements IAclUserRoleDao {
    @Override
    public void deleteByEmpId(Integer empId){
        getSqlSession().delete(CLASS_NAME + "-Delete-By-Emp-Id", empId);
    }

    @Override
    public List<AclUserRole> selectByEmpId(Integer empId) {
        return getSqlSession().selectList(CLASS_NAME + "-Select-By-Emp-Id", empId);
    }


}
