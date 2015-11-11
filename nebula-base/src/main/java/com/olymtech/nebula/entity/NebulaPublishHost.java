package com.olymtech.nebula.entity;

import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;

public class NebulaPublishHost extends BaseDO {

    private Integer publishModuleId;

    private Integer publishEventId;

    private String passPublishHostName;

    private String passPublishHostIp;

    private PublishAction actionName;

    private PublishActionGroup actionGroup;

    private Boolean isSuccessAction;

    private String actionResult;

    public PublishAction getActionName() {
        return actionName;
    }

    public void setActionName(PublishAction actionName) {
        this.actionName = actionName;
    }

    public PublishActionGroup getActionGroup() {
        return actionGroup;
    }

    public void setActionGroup(PublishActionGroup actionGroup) {
        this.actionGroup = actionGroup;
    }

    public Boolean getIsSuccessAction() {
        return isSuccessAction;
    }

    public void setIsSuccessAction(Boolean isSuccessAction) {
        this.isSuccessAction = isSuccessAction;
    }

    public String getActionResult() {
        return actionResult;
    }

    public void setActionResult(String actionResult) {
        this.actionResult = actionResult;
    }

    public Integer getPublishModuleId() {
        return publishModuleId;
    }

    public void setPublishModuleId(Integer publishModuleId) {
        this.publishModuleId = publishModuleId;
    }

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public String getPassPublishHostName() {
        return passPublishHostName;
    }

    public void setPassPublishHostName(String passPublishHostName) {
        this.passPublishHostName = passPublishHostName == null ? null : passPublishHostName.trim();
    }

    public String getPassPublishHostIp() {
        return passPublishHostIp;
    }

    public void setPassPublishHostIp(String passPublishHostIp) {
        this.passPublishHostIp = passPublishHostIp == null ? null : passPublishHostIp.trim();
    }
}