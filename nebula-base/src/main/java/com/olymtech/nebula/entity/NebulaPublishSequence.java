/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

/**
 * Created by Gavin on 2015-11-02 14:11.
 */
public class NebulaPublishSequence extends BaseDO {
    private String actionName;

    private String actionDesc;

    private Integer actionSeqId;

    private String actionGroup;

    private String actionClass;

    public String getActionClass() {
        return actionClass;
    }

    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

    public String getActionGroup() {
        return actionGroup;
    }

    public void setActionGroup(String actionGroup) {
        this.actionGroup = actionGroup;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName == null ? null : actionName.trim();
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc == null ? null : actionDesc.trim();
    }

    public Integer getActionSeqId() {
        return actionSeqId;
    }

    public void setActionSeqId(Integer actionSeqId) {
        this.actionSeqId = actionSeqId;
    }
}
