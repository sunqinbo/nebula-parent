/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action;

import com.olymtech.nebula.entity.NebulaPublishEvent;

/**
 * @author taoshanchang 15/11/4
 */
public abstract class AbstractAction implements Action {

    protected String actionName;

    public String getActionName() {
        if (actionName == null) {
            return this.getClass().getSimpleName();
        }
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Override
    public abstract boolean doAction(NebulaPublishEvent event) throws Exception;

    @Override
    public abstract void doFailure(NebulaPublishEvent event);
}
