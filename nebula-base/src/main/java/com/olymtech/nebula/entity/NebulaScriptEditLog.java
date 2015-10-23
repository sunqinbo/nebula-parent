package com.olymtech.nebula.entity;

import java.util.Date;

public class NebulaScriptEditLog extends BaseDO{

    private Integer scriptId;

    private String logAction;

    private String logInfo;

    private Date logDatetime;

    private Integer optEmpId;

    public Integer getScriptId() {
        return scriptId;
    }

    public void setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
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