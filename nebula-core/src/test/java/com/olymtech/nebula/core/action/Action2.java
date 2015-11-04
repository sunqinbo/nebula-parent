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
public class Action2 extends AbstractAction {

    public Action2(String actionName) {
        super.setActionName(actionName);
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        System.out.println("svn动作");
        return false;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {
        System.out.println("回滚svn动作");
    }
}
