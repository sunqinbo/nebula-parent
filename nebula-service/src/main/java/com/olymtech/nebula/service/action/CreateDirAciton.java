/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.salt.ISaltStackService;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishHost;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.suse.saltstack.netapi.datatypes.target.MinionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taoshanchang 15/11/4
 */

@Service
public class CreateDirAciton extends AbstractAction {

    @Autowired
    private ISaltStackService saltStackService;

    @Autowired
    private IPublishScheduleService publishScheduleService;

    public CreateDirAciton() {

    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CREATE_PUBLISH_DIR, PublishActionGroup.PRE_MINION, null ,"");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {
            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targes = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targes.add(nebulaPublishHost.getPassPublishHostIp());
            }

            boolean warsResult = saltStackService.mkDir(new MinionList(targes), "/home/saas/tomcat/public_wars/"+publishModule.getPublishModuleKey(), true);
            boolean etcResult = saltStackService.mkDir(new MinionList(targes), "/home/saas/tomcat/public_etcs/"+publishModule.getPublishModuleKey(), true);

            if (!warsResult||!etcResult) {
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CREATE_PUBLISH_DIR, PublishActionGroup.PRE_MINION, false ,"error message");
                return false;
            }
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CREATE_PUBLISH_DIR, PublishActionGroup.PRE_MINION, true ,"");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
