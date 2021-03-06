package com.olymtech.nebula.entity;

public class AclUserRole extends BaseDO{

    private Integer empId;

    private Integer roleId;

    private AclRole aclRole;

    private NebulaUserInfo nebulaUserInfo;

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

    public NebulaUserInfo getNebulaUserInfo() {
        return nebulaUserInfo;
    }

    public void setNebulaUserInfo(NebulaUserInfo nebulaUserInfo) {
        this.nebulaUserInfo = nebulaUserInfo;
    }

    public AclRole getAclRole() {
        return aclRole;
    }

    public void setAclRole(AclRole aclRole) {
        this.aclRole = aclRole;
    }
}