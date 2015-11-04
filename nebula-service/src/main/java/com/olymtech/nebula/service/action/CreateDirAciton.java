/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.entity.NebulaPublishEvent;

/**
 * @author taoshanchang 15/11/4
 */
public class CreateDirAciton extends AbstractAction {



    public CreateDirAciton() {

    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        return false;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
