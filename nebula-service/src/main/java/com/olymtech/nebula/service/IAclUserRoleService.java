package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.AclUserRole;

/**
 * Created by WYQ on 2015/11/17.
 */
public interface IAclUserRoleService {
    public int insertAclUserRole(AclUserRole aclUserRole);

    public void deleteAclUserRoleById(Integer id);

    public void updateAclUserRole(AclUserRole aclUserRole);
}
