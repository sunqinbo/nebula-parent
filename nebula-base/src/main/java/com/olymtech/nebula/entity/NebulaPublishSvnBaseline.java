package com.olymtech.nebula.entity;

public class NebulaPublishSvnBaseline extends BaseDO{

    private String productName;

    private String productEnv;

    private String productSrcSvn;

    private Integer publishEventId;

    private String srcSvnVersion;

    public NebulaPublishSvnBaseline(){
        super();
    }

    public NebulaPublishSvnBaseline(String productName,
                                    String productEnv,
                                    String productSrcSvn,
                                    Integer publishEventId,
                                    String srcSvnVersion){
        super();
        this.productName = productName;
        this.productEnv = productEnv;
        this.productSrcSvn = productSrcSvn;
        this.publishEventId = publishEventId;
        this.srcSvnVersion = srcSvnVersion;
    }

    public String getProductEnv() {
        return productEnv;
    }

    public void setProductEnv(String productEnv) {
        this.productEnv = productEnv;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductSrcSvn() {
        return productSrcSvn;
    }

    public void setProductSrcSvn(String productSrcSvn) {
        this.productSrcSvn = productSrcSvn == null ? null : productSrcSvn.trim();
    }

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public String getSrcSvnVersion() {
        return srcSvnVersion;
    }

    public void setSrcSvnVersion(String srcSvnVersion) {
        this.srcSvnVersion = srcSvnVersion == null ? null : srcSvnVersion.trim();
    }
}