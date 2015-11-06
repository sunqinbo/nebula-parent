package com.olymtech.nebula.entity;

import com.olymtech.nebula.entity.enums.PublishAction;

public class NebulaPublishSchedule extends BaseDO{

    private Integer publishEventId;

    private PublishAction publishAction;

    private Boolean isSuccessAction;

    private String errorMsg;

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public PublishAction getPublishAction() {
        return publishAction;
    }

    public void setPublishAction(PublishAction publishAction) {
        this.publishAction = publishAction;
    }

    public Boolean getIsSuccessAction() {
        return isSuccessAction;
    }

    public void setIsSuccessAction(Boolean isSuccessAction) {
        this.isSuccessAction = isSuccessAction;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }
}