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
import com.olymtech.nebula.service.IPublishEventService;
import com.suse.saltstack.netapi.datatypes.target.MinionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taoshanchang 15/11/4
 */
@Service
public class CpEtcAction extends AbstractAction {

    @Autowired
    private IPublishEventService publishEventService;

    @Autowired
    private ISaltStackService saltStackService;

    public static final String WarDirPrefix = "/home/saas/tomcat/public_wars/";
    public static final String EtcDirPrefix = "/home/saas/tomcat/public_etcs/";

    public CpEtcAction() {
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targes = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targes.add(nebulaPublishHost.getPassPublishHostIp());
            }

            boolean warsResult = saltStackService.cpDir(new MinionList(targes), null, WarDirPrefix + publishModule.getPublishModuleKey());
            boolean etcResult = saltStackService.cpDir(new MinionList(targes), null, EtcDirPrefix + publishModule.getPublishModuleKey());

            if (!warsResult || !etcResult) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
