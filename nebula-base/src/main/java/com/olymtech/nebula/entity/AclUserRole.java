package com.olymtech.nebula.entity;

import java.util.List;

public class AclUserRole extends BaseDO{

    private Integer empId;

    private Integer roleId;

    private List<AclRole> aclRoleList;

    private List<NebulaUserInfo> nebulaUserInfoList;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<AclRole> getAclRoleList() {
        return aclRoleList;
    }

    public void setAclRoleList(List<AclRole> aclRoleList) {
        this.aclRoleList = aclRoleList;
    }

    public List<NebulaUserInfo> getNebulaUserInfoList() {
        return nebulaUserInfoList;
    }

    public void setNebulaUserInfoList(List<NebulaUserInfo> nebulaUserInfoList) {
        this.nebulaUserInfoList = nebulaUserInfoList;
    }
}