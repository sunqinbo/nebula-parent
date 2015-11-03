package com.olymtech.nebula.entity;

public class NebulaPublishHost extends BaseDO {

    private Integer publishModuleId;

    private Integer publishEventId;

    private String passPublishHostName;

    private String passPublishHostIp;

    public Integer getPublishModuleId() {
        return publishModuleId;
    }

    public void setPublishModuleId(Integer publishModuleId) {
        this.publishModuleId = publishModuleId;
    }

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public String getPassPublishHostName() {
        return passPublishHostName;
    }

    public void setPassPublishHostName(String passPublishHostName) {
        this.passPublishHostName = passPublishHostName == null ? null : passPublishHostName.trim();
    }

    public String getPassPublishHostIp() {
        return passPublishHostIp;
    }

    public void setPassPublishHostIp(String passPublishHostIp) {
        this.passPublishHostIp = passPublishHostIp == null ? null : passPublishHostIp.trim();
    }
}