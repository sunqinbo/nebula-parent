/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.service.IPublishEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author taoshanchang 15/11/4
 */

@Service
public class CreateDirAciton extends AbstractAction {

    @Autowired
    private IPublishEventService publishEventService;


    public CreateDirAciton() {

    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        System.out.println("CreateDirAciton");
        System.out.println(publishEventService);




        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
