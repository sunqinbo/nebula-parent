package com.olymtech.nebula.entity;

public class NebulaPublishModule extends BaseDO {

    private Integer publishEventId;

    private String publishModule;

    private String publishModuleKey;

    private String moduleSrcSvn;

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public String getPublishModule() {
        return publishModule;
    }

    public void setPublishModule(String publishModule) {
        this.publishModule = publishModule == null ? null : publishModule.trim();
    }

    public String getPublishModuleKey() {
        return publishModuleKey;
    }

    public void setPublishModuleKey(String publishModuleKey) {
        this.publishModuleKey = publishModuleKey == null ? null : publishModuleKey.trim();
    }

    public String getModuleSrcSvn() {
        return moduleSrcSvn;
    }

    public void setModuleSrcSvn(String moduleSrcSvn) {
        this.moduleSrcSvn = moduleSrcSvn == null ? null : moduleSrcSvn.trim();
    }
}