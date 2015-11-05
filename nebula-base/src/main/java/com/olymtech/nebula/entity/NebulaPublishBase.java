package com.olymtech.nebula.entity;

public class NebulaPublishBase extends BaseDO{

    private Integer publishEventId;

    private String publishProductName;

    private String publishModuleName;

    private String publishModuleKey;

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public String getPublishProductName() {
        return publishProductName;
    }

    public void setPublishProductName(String publishProductName) {
        this.publishProductName = publishProductName == null ? null : publishProductName.trim();
    }

    public String getPublishModuleName() {
        return publishModuleName;
    }

    public void setPublishModuleName(String publishModuleName) {
        this.publishModuleName = publishModuleName == null ? null : publishModuleName.trim();
    }

    public String getPublishModuleKey() {
        return publishModuleKey;
    }

    public void setPublishModuleKey(String publishModuleKey) {
        this.publishModuleKey = publishModuleKey == null ? null : publishModuleKey.trim();
    }

}