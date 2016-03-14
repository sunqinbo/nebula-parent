package com.olymtech.nebula.entity;

import com.olymtech.nebula.entity.enums.LogAction;

import java.util.Date;

public class NebulaPublishEventLog extends BaseDO {

    private Integer publishEventId;

    private LogAction logAction;

    private String logInfo;

    private Date logDatetime;

    private Integer optEmpId;

    public NebulaPublishEventLog() {
        super();
    }

    public NebulaPublishEventLog(Integer publishEventId, LogAction logAction, String logInfo, Integer optEmpId) {
        super();
        this.publishEventId = publishEventId;
        this.logAction = logAction;
        this.logInfo = logInfo;
        this.optEmpId = optEmpId;
    }

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public LogAction getLogAction() {
        return logAction;
    }

    public void setLogAction(LogAction logAction) {
        this.logAction = logAction;
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