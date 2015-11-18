package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.AclRole;
import com.olymtech.nebula.entity.DataTablePage;

/**
 * Created by WYQ on 2015/11/18.
 */
public interface IAclRoleService {
    public int insertAclRole(AclRole aclRole);

    public void deleteAclRoleById(Integer id);

    public void updateAclRole(AclRole aclRole);

    public PageInfo getPageInfoAclRole(DataTablePage dataTablePage);
}
