package com.olymtech.nebula.entity;

public class NebulaPublishSvnBaseline extends BaseDO{

    private String productName;

    private String productSrcSvn;

    private Integer publishEventId;

    private String srcSvnVersion;

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