/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.entity.NebulaPublishEvent;

/**
 * @author taoshanchang 15/11/4
 */
public class Action3 extends AbstractAction {

    public Action3(String actionName) {
        this.actionName = actionName;
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        System.out.println(this.getActionName());
        System.out.println("salt动作");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {
        System.out.println("salt动作失败");
    }
}
