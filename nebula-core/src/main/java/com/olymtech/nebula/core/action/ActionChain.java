/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taoshanchang 15/11/4
 */
public class ActionChain {

    List<Action> actions = new ArrayList<Action>();

    public ActionChain() {
    }

    public ActionChain(List<Action> actions) {
        this.actions = actions;
    }

    public ActionChain addAction(Action action) {
        this.actions.add(action);
        return this;
    }

    public ActionChain addActions(List<Action> actions) {
        this.actions.addAll(actions);
        return this;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
