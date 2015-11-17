package com.olymtech.nebula.entity;

public class AclPermission extends BaseDO{

    private String permissionName;

    private String permissionCname;

    private String permissionDesc;

    private String permission;

    private String permissionType;

    private String url;

    private Integer pid;

    private String pids;

    private String isEnable;

    public String getPermissionCname() {
        return permissionCname;
    }

    public void setPermissionCname(String permissionCname) {
        this.permissionCname = permissionCname;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc == null ? null : permissionDesc.trim();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids == null ? null : pids.trim();
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable == null ? null : isEnable.trim();
    }
}