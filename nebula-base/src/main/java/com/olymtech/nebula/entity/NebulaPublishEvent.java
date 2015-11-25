package com.olymtech.nebula.entity;

import com.olymtech.nebula.common.utils.CustomDateSerializer;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.entity.enums.PublishStatus;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class NebulaPublishEvent extends BaseDO {

    private String publishSubject;

    private String publishBuName;

    private String publishBuCname;

    private String publishProductName;

    private String publishProductCname;

    private String publishEnv;

    private String publishSvn;

    private String productSrcSvn;

    private String publishProductKey;

    private Date publishDatetime;

    private Date submitDatetime;

    private Integer submitEmpId;

    private Integer publishEmpId;

    private Boolean isSuccessPublish;

    private Boolean isDelete = false;

    private PublishStatus publishStatus;

    private Boolean isApproved = false;

    private PublishActionGroup publishActionGroup;

    private List<NebulaPublishModule> publishModules;

    public Integer getPublishEmpId() {
        return publishEmpId;
    }

    public void setPublishEmpId(Integer publishEmpId) {
        this.publishEmpId = publishEmpId;
    }

    public PublishActionGroup getPublishActionGroup() {
        return publishActionGroup;
    }

    public void setPublishActionGroup(PublishActionGroup publishActionGroup) {
        this.publishActionGroup = publishActionGroup;
    }

    public List<NebulaPublishModule> getPublishModules() {
        return publishModules;
    }

    public void setPublishModules(List<NebulaPublishModule> publishModules) {
        this.publishModules = publishModules;
    }

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

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getPublishDatetime() {
        return publishDatetime;
    }

    public void setPublishDatetime(Date publishDatetime) {
        this.publishDatetime = publishDatetime;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
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

    public String getPublishSubject() {
        return publishSubject;
    }

    public void setPublishSubject(String publishSubject) {
        this.publishSubject = publishSubject;
    }

    public String getPublishBuName() {
        return publishBuName;
    }

    public void setPublishBuName(String publishBuName) {
        this.publishBuName = publishBuName;
    }

    public String getPublishBuCname() {
        return publishBuCname;
    }

    public void setPublishBuCname(String publishBuCname) {
        this.publishBuCname = publishBuCname;
    }

    public Boolean getIsSuccessPublish() {
        return isSuccessPublish;
    }

    public void setIsSuccessPublish(Boolean isSuccessPublish) {
        this.isSuccessPublish = isSuccessPublish;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public PublishStatus getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(PublishStatus publishStatus) {
        this.publishStatus = publishStatus;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }
}