package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.AclPermission;
import com.olymtech.nebula.entity.DataTablePage;

/**
 * Created by WYQ on 2015/11/17.
 */
public interface IAclPermissionService {

    public int insertAclPermission(AclPermission permission);

    public void deleteAclPermissionById(Integer id);

    public void updateAclPermission(AclPermission permission);

    public PageInfo getPageInfoAclPermission(DataTablePage dataTablePage);
}
