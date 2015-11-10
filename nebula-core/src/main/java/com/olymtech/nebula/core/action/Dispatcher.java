/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action;

import com.olymtech.nebula.entity.NebulaPublishEvent;

import java.util.List;

/**
 * @author taoshanchang 15/11/4
 */
public class Dispatcher {

    private ActionChain actionChain = null;

    private int actionIndex = 0;

    public Dispatcher(ActionChain actionChain) {
        this.actionChain = actionChain;
    }


    public void doDispatch(NebulaPublishEvent event) throws Exception {
        List<Action> actions = actionChain.getActions();
        if (actions != null || actions.size() == 0) {
            for (int i = 0; i < actions.size(); i++) {
                if (!actions.get(i).doAction(event)) {
                    triggerFailure(event);
                    return;
                }
                actionIndex = i;
            }
        } else {

            throw new Exception("动作为空");
        }

    }

    public void triggerFailure(NebulaPublishEvent event) {
        List<Action> actions = actionChain.getActions();
        try {
            for (int i = actionIndex; i >= 0; i--) {
                actions.get(i).doFailure(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ActionChain getActionChain() {
        return actionChain;
    }

    public void setActionChain(ActionChain actionChain) {
        this.actionChain = actionChain;
    }
}
