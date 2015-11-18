package com.olymtech.nebula.entity;

public class NebulaPublishBase extends BaseDO{

    private Integer publishEventId;

    private String publishProductName;

    private String publishProductEnv;

    private String publishModuleName;

    private String publishModuleKey;

    public NebulaPublishBase(){
        super();
    }

    public NebulaPublishBase(String publishProductName,String publishModuleName,String publishProductEnv){
        super();
        this.publishProductName = publishProductName;
        this.publishModuleName = publishModuleName;
        this.publishProductEnv = publishProductEnv;
    }

    public NebulaPublishBase(Integer publishEventId,
                             String publishProductName,
                             String publishProductEnv,
                             String publishModuleName,
                             String publishModuleKey){
        super();
        this.publishEventId = publishEventId;
        this.publishProductName = publishProductName;
        this.publishProductEnv = publishProductEnv;
        this.publishModuleName = publishModuleName;
        this.publishModuleKey = publishModuleKey;

    }

    public String getPublishProductEnv() {
        return publishProductEnv;
    }

    public void setPublishProductEnv(String publishProductEnv) {
        this.publishProductEnv = publishProductEnv;
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

}