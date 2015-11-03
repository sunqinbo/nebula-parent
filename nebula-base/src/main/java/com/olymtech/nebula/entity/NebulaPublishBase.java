package com.olymtech.nebula.entity;

public class NebulaPublishBase {
    private Integer id;

    private Integer publishEventId;

    private String publishProductName;

    private String publishModuleName;

    private String publishModuleKey;

    private Integer isSuccessPublish;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getIsSuccessPublish() {
        return isSuccessPublish;
    }

    public void setIsSuccessPublish(Integer isSuccessPublish) {
        this.isSuccessPublish = isSuccessPublish;
    }
}