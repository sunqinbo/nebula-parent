package com.olymtech.nebula.entity;

import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;

public class NebulaPublishSchedule extends BaseDO{

    private Integer publishEventId;

    private PublishAction publishAction;

    private PublishActionGroup publishActionGroup;

    private Boolean isSuccessAction;

    private String errorMsg;

    private Integer errorType;

    public Integer getErrorType() {
        return errorType;
    }

    public void setErrorType(Integer errorType) {
        this.errorType = errorType;
    }

    public Boolean getSuccessAction() {
        return isSuccessAction;
    }

    public void setSuccessAction(Boolean successAction) {
        isSuccessAction = successAction;
    }

    public NebulaPublishSchedule(){
        super();
    }

    public NebulaPublishSchedule(Integer publishEventId,
                                 PublishAction publishAction,
                                 PublishActionGroup publishActionGroup,
                                 Boolean isSuccessAction,
                                 String errorMsg){
        super();
        this.publishEventId = publishEventId;
        this.publishAction = publishAction;
        this.publishActionGroup = publishActionGroup;
        this.isSuccessAction = isSuccessAction;
        this.errorMsg = errorMsg;
    }

    public NebulaPublishSchedule(Integer publishEventId,
                                 PublishAction publishAction,
                                 PublishActionGroup publishActionGroup){
        super();
        this.publishEventId = publishEventId;
        this.publishAction = publishAction;
        this.publishActionGroup = publishActionGroup;
    }

    public PublishActionGroup getPublishActionGroup() {
        return publishActionGroup;
    }

    public void setPublishActionGroup(PublishActionGroup publishActionGroup) {
        this.publishActionGroup = publishActionGroup;
    }

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