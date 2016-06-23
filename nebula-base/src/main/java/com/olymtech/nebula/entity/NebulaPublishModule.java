package com.olymtech.nebula.entity;

import java.util.List;

public class NebulaPublishModule extends BaseDO {

    private Integer publishEventId;

    private String publishProductName;

    private String publishModuleName;

    private String publishModuleKey;

    private String moduleSrcSvn;

    /** 当前发布批次 */
    private Integer nowBatchTag;

    /** 发布总批次 */
    private Integer batchTotal;

    private List<NebulaPublishHost> publishHosts;

    private List<NebulaPublishApp>  publishApps;

    private List<NebulaPublishSlb> publishSlbs;

    public List<NebulaPublishSlb> getPublishSlbs() {
        return publishSlbs;
    }

    public void setPublishSlbs(List<NebulaPublishSlb> publishSlbs) {
        this.publishSlbs = publishSlbs;
    }

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

    public Integer getNowBatchTag() {
        return nowBatchTag;
    }

    public void setNowBatchTag(Integer nowBatchTag) {
        this.nowBatchTag = nowBatchTag;
    }

    public Integer getBatchTotal() {
        return batchTotal;
    }

    public void setBatchTotal(Integer batchTotal) {
        this.batchTotal = batchTotal;
    }
}