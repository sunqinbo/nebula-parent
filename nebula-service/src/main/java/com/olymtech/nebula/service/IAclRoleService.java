package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.AclRole;
import com.olymtech.nebula.entity.DataTablePage;

import java.util.List;

/**
 * Created by WYQ on 2015/11/18.
 */
public interface IAclRoleService {

    public int insertAclRole(AclRole aclRole, List<Integer> permissionIds);

    public void deleteAclRoleById(Integer id);

    public void updateAclRole(AclRole aclRole, List<Integer> permissionIds);

    public PageInfo getPageInfoAclRole(DataTablePage dataTablePage);

    public AclRole getAclRoleWithPermissionsByRoleId(Integer roleId);
}
