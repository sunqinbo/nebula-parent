package com.olymtech.nebula.entity;

import com.olymtech.nebula.common.utils.CustomDateSerializer;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.entity.enums.PublishStatus;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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

    private NebulaUserInfo submitUser;

    private Integer publishEmpId;

    private Integer pid;

    private NebulaUserInfo publishUser;

    private Boolean isSuccessPublish;

    private Boolean isDelete = false;

    private PublishStatus publishStatus;

    private Boolean isApproved = false;

    private PublishActionGroup publishActionGroup;

    private String starTime;

    private String endTime;

    private Integer approveEmpId;

    private NebulaUserInfo approveUser;

    private Integer countError;

    private Integer countException;

    private Date publishEndDatetime;

    private String changeList;

    private String publishRemark;

    public String getChangeList() {
        return changeList;
    }

    public void setChangeList(String changeList) {
        this.changeList = changeList;
    }

    public String getPublishRemark() {
        return publishRemark;
    }

    public void setPublishRemark(String publishRemark) {
        this.publishRemark = publishRemark;
    }

    public Integer getCountError() {
        return countError;
    }

    public void setCountError(Integer countError) {
        this.countError = countError;
    }

    public Integer getCountException() {
        return countException;
    }

    public void setCountException(Integer countException) {
        this.countException = countException;
    }

    public Date getPublishEndDatetime() {
        return publishEndDatetime;
    }

    public void setPublishEndDatetime(Date publishEndDatetime) {
        this.publishEndDatetime = publishEndDatetime;
    }

    public NebulaUserInfo getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(NebulaUserInfo approveUser) {
        this.approveUser = approveUser;
    }

    private Date approveDatetime;

    private List<NebulaPublishModule> publishModules;

    public Date getApproveDatetime() {
        return approveDatetime;
    }

    public void setApproveDatetime(Date approveDatetime) {
        this.approveDatetime = approveDatetime;
    }

    public Boolean getSuccessPublish() {
        return isSuccessPublish;
    }

    public void setSuccessPublish(Boolean successPublish) {
        isSuccessPublish = successPublish;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public Integer getApproveEmpId() {
        return approveEmpId;
    }

    public void setApproveEmpId(Integer approveEmpId) {
        this.approveEmpId = approveEmpId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public NebulaUserInfo getSubmitUser() {
        return submitUser;
    }

    public void setSubmitUser(NebulaUserInfo submitUser) {
        this.submitUser = submitUser;
    }

    public NebulaUserInfo getPublishUser() {
        return publishUser;
    }

    public void setPublishUser(NebulaUserInfo publishUser) {
        this.publishUser = publishUser;
    }

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

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}