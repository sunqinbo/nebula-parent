/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.IAclUserRoleDao;
import com.olymtech.nebula.entity.AclUserRole;
import org.springframework.stereotype.Repository;

/**
 * Created by Gavin on 2015-11-10 00:54.
 */
@Repository("aclUserRoleDao")
public class AclUserRoleDaoImpl extends BaseDaoImpl<AclUserRole,Integer> implements IAclUserRoleDao {
}
