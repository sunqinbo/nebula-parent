/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;

/**
 * Created by Gavin on 2015-11-02 14:11.
 */
public class NebulaPublishSequence extends BaseDO {
    private PublishAction actionName;

    private String actionDesc;

    private Integer actionSeqId;

    private PublishActionGroup actionGroup;

    private String actionClass;

    public String getActionClass() {
        return actionClass;
    }

    public void setActionClass(String actionClass) {
        this.actionClass = actionClass;
    }

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
