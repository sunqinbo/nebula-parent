package com.olymtech.nebula.entity;

public class NebulaPublishApp extends BaseDO{

    private Integer publishEventId;

    private Integer publishModuleId;

    private String publishAppName;

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public Integer getPublishModuleId() {
        return publishModuleId;
    }

    public void setPublishModuleId(Integer publishModuleId) {
        this.publishModuleId = publishModuleId;
    }

    public String getPublishAppName() {
        return publishAppName;
    }

    public void setPublishAppName(String publishAppName) {
        this.publishAppName = publishAppName == null ? null : publishAppName.trim();
    }
}