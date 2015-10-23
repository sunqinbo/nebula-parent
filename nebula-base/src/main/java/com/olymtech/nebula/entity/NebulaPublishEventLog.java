package com.olymtech.nebula.entity;

import java.util.Date;

public class NebulaPublishEventLog extends BaseDO{

    private Integer publishEventId;

    private String logAction;

    private String logInfo;

    private Date logDatetime;

    private Integer optEmpId;

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public String getLogAction() {
        return logAction;
    }

    public void setLogAction(String logAction) {
        this.logAction = logAction == null ? null : logAction.trim();
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo == null ? null : logInfo.trim();
    }

    public Date getLogDatetime() {
        return logDatetime;
    }

    public void setLogDatetime(Date logDatetime) {
        this.logDatetime = logDatetime;
    }

    public Integer getOptEmpId() {
        return optEmpId;
    }

    public void setOptEmpId(Integer optEmpId) {
        this.optEmpId = optEmpId;
    }
}