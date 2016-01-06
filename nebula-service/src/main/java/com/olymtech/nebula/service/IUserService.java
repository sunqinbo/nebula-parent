/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.NebulaUserInfo;

import java.util.List;
import java.util.Set;

/**
 * Created by taoshanchang on 15/11/9.
 */
public interface IUserService {


    public NebulaUserInfo findByUsername(String username);

    public Set<String> findRolesByEmpId(Integer empId);

    public Set<String> findPermissionsByEmpId(Integer empId);

    public int insertNebulaUserInfo(NebulaUserInfo nebulaUserInfo, Integer[] roleIds);

    public void deleteNebulaUserInfo(Integer id);

    public void updateNebulaUserInfo(NebulaUserInfo nebulaUserInfo, List<Integer> roleIds);

    public void updatePassword(Integer userId, String newPassword);

    public PageInfo getPageInfoAclUser(DataTablePage dataTablePage);

    public NebulaUserInfo getAclUserWithRolesByEmpId(Integer empId);

    public NebulaUserInfo selectByEmpId(Integer empId);

    void updateMyPassword(NebulaUserInfo userInfo);
}
