package com.olymtech.nebula.entity;

import java.util.Date;

public class NebulaPublishEvent extends BaseDO {

    private String publishProductName;

    private String publishProductCname;

    private String publishEnv;

    private String publishSvn;

    private String productSrcSvn;

    private String publishProductKey;

    private Date publishDatetime;

    private Date submitDatetime;

    private Integer submitEmpId;

    private Integer isSuccessPublish;

    public String getPublishProductName() {
        return publishProductName;
    }

    public void setPublishProductName(String publishProductName) {
        this.publishProductName = publishProductName == null ? null : publishProductName.trim();
    }

    public String getPublishProductCname() {
        return publishProductCname;
    }

    public void setPublishProductCname(String publishProductCname) {
        this.publishProductCname = publishProductCname == null ? null : publishProductCname.trim();
    }

    public String getPublishEnv() {
        return publishEnv;
    }

    public void setPublishEnv(String publishEnv) {
        this.publishEnv = publishEnv == null ? null : publishEnv.trim();
    }

    public String getPublishSvn() {
        return publishSvn;
    }

    public void setPublishSvn(String publishSvn) {
        this.publishSvn = publishSvn == null ? null : publishSvn.trim();
    }

    public String getProductSrcSvn() {
        return productSrcSvn;
    }

    public void setProductSrcSvn(String productSrcSvn) {
        this.productSrcSvn = productSrcSvn == null ? null : productSrcSvn.trim();
    }

    public String getPublishProductKey() {
        return publishProductKey;
    }

    public void setPublishProductKey(String publishProductKey) {
        this.publishProductKey = publishProductKey == null ? null : publishProductKey.trim();
    }

    public Date getPublishDatetime() {
        return publishDatetime;
    }

    public void setPublishDatetime(Date publishDatetime) {
        this.publishDatetime = publishDatetime;
    }

    public Date getSubmitDatetime() {
        return submitDatetime;
    }

    public void setSubmitDatetime(Date submitDatetime) {
        this.submitDatetime = submitDatetime;
    }

    public Integer getSubmitEmpId() {
        return submitEmpId;
    }

    public void setSubmitEmpId(Integer submitEmpId) {
        this.submitEmpId = submitEmpId;
    }

    public Integer getIsSuccessPublish() {
        return isSuccessPublish;
    }

    public void setIsSuccessPublish(Integer isSuccessPublish) {
        this.isSuccessPublish = isSuccessPublish;
    }
}