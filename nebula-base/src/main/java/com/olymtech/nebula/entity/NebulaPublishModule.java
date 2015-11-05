package com.olymtech.nebula.entity;

import java.util.List;

public class NebulaPublishModule extends BaseDO {

    private Integer publishEventId;

    private String publishProductName;

    private String publishModuleName;

    private String publishModuleKey;

    private String moduleSrcSvn;

    private List<NebulaPublishHost> publishHosts;

    private List<NebulaPublishApp>  publishApps;

    public List<NebulaPublishHost> getPublishHosts() {
        return publishHosts;
    }

    public void setPublishHosts(List<NebulaPublishHost> publishHosts) {
        this.publishHosts = publishHosts;
    }

    public List<NebulaPublishApp> getPublishApps() {
        return publishApps;
    }

    public void setPublishApps(List<NebulaPublishApp> publishApps) {
        this.publishApps = publishApps;
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
        this.publishProductName = publishProductName;
    }

    public String getPublishModuleName() {
        return publishModuleName;
    }

    public void setPublishModuleName(String publishModuleName) {
        this.publishModuleName = publishModuleName;
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