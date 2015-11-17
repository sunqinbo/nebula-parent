package com.olymtech.nebula.entity;

public class AclRole extends BaseDO{

    private String roleName;

    private String roleCname;

    private String roleDesc;

    private Boolean isEnable;

    public String getRoleCname() {
        return roleCname;
    }

    public void setRoleCname(String roleCname) {
        this.roleCname = roleCname;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }
}