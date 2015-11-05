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
import com.olymtech.nebula.service.IPublishAppService;
import com.olymtech.nebula.service.IPublishBaseService;
import com.suse.saltstack.netapi.datatypes.target.MinionList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taoshanchang 15/11/5
 */

@Service
public class PublicAction extends AbstractAction {

    @Autowired
    private IPublishAppService publishAppService;

    @Autowired
    private ISaltStackService saltStackService;

    public static final String WarDirPrefix = "/home/saas/tomcat/public_wars/";
    public static final String EtcDirPrefix = "/home/saas/tomcat/public_etcs/";

    public static final String LocalBaseDir = "/home/saas/deploy_tmp/";


    public PublicAction() {
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

            String warFromBase = LocalBaseDir + event.getPublishProductKey() + "/publish_war/";
            String etcFrom = LocalBaseDir + event.getPublishProductKey() + "/src_svn/etc/";

            List<NebulaPublishApp> appList = publishAppService.selectByEventIdAndModuleId(event.getId(), publishModule.getId());


            for (NebulaPublishApp app : appList) {
                boolean result = saltStackService.cpFileRemote(new MinionList(targes), warFromBase + app.getPublishAppName(), WarDirPrefix + publishModule.getPublishModuleKey());
                if (!result) {
                    return false;
                }
            }


            boolean etcResult = saltStackService.cpDirRemote(new MinionList(targes), etcFrom, EtcDirPrefix + publishModule.getPublishModuleKey() + ".war");

            if (!etcResult) {
                return false;
            }

        }

        return true;

    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }

}
