/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.NebulaPublishEvent;
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

    public PageInfo getPageInfoAclUser(DataTablePage dataTablePage, NebulaUserInfo nebulaUserInfo);

    public NebulaUserInfo getAclUserWithRolesByEmpId(Integer empId);

    public NebulaUserInfo selectByEmpId(Integer empId);

    NebulaUserInfo selectById(Integer id);

    void updateMyPassword(NebulaUserInfo userInfo);

    void updateByIdSelective(NebulaUserInfo nebulaUserInfo);

    /*判断登录人角色是否是所需角色*/
    Boolean userRoleIsNeedRole(NebulaUserInfo user, String roleName);

    /*判断登录人是否是提交人,是否是管理员,是否是超级管理员,满足之一才有权限*/
    Boolean ifLoginUserValid(NebulaUserInfo loginUser, NebulaPublishEvent publishEvent);
}
