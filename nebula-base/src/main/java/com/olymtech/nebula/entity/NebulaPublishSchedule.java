package com.olymtech.nebula.entity;

public class NebulaPublishSchedule extends BaseDO{

    private Integer publishEventId;

    private String publishAction;

    private Integer isSuccessAction;

    private String errorMsg;

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public String getPublishAction() {
        return publishAction;
    }

    public void setPublishAction(String publishAction) {
        this.publishAction = publishAction == null ? null : publishAction.trim();
    }

    public Integer getIsSuccessAction() {
        return isSuccessAction;
    }

    public void setIsSuccessAction(Integer isSuccessAction) {
        this.isSuccessAction = isSuccessAction;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }
}