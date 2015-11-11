/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action;

import com.olymtech.nebula.entity.NebulaPublishEvent;

/**
 * @author taoshanchang 15/11/4
 */
public class Action3 extends AbstractAction {

    public Action3(String actionName) {
        super.setActionName(actionName);
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        System.out.println("salt动作");
        this.nodifyObservers("salt动作执行完成");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {
        System.out.println("回滚salt动作");
    }
}
