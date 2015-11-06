/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.salt.ISaltStackService;
import com.olymtech.nebula.entity.NebulaPublishApp;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishHost;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.suse.saltstack.netapi.datatypes.target.MinionList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taoshanchang 15/11/6
 */
public class ChangeLnAction extends AbstractAction {

    @Autowired
    private ISaltStackService saltStackService;

    public static final String WarDirPrefix = "/home/saas/tomcat/public_wars/";
    public static final String EtcDirPrefix = "/home/saas/tomcat/public_etcs/";

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targes = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targes.add(nebulaPublishHost.getPassPublishHostIp());
            }

            List<String> pathList = new ArrayList<String>();
            pathList.add("/home/saas/tomcat/etc");
            pathList.add("/home/saas/tomcat/webapps");

            boolean etcResult = saltStackService.deleteFile(new MinionList(targes), pathList, true);

            boolean result = saltStackService.makeLn(new MinionList(targes), WarDirPrefix + publishModule.getPublishModuleKey(), "/home/saas/tomcat/webapps");
            boolean result2 = saltStackService.makeLn(new MinionList(targes), EtcDirPrefix + publishModule.getPublishModuleKey(), "/home/saas/tomcat/etc");

            if (!etcResult||!result||!result2) {
                return false;
            }

        }

        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
