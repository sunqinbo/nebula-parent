package com.olymtech.nebula.entity;

public class AclRolePermission extends BaseDO{

    private Integer roleId;

    private Integer permissionId;

    private AclPermission aclPermission;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public AclPermission getAclPermission() {
        return aclPermission;
    }

    public void setAclPermission(AclPermission aclPermission) {
        this.aclPermission = aclPermission;
    }
}