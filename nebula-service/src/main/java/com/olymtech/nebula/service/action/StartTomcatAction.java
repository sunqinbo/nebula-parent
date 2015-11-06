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
import com.suse.saltstack.netapi.datatypes.target.MinionList;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfo;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author taoshanchang 15/11/6
 */
@Service
public class StartTomcatAction extends AbstractAction {

    @Autowired
    private ISaltStackService saltStackService;

    public static final String startCommandPath = "/home/saas/tomcat/bin/start_tomcat.sh";
    public static final String stopCommandPath = "/home/saas/tomcat/bin/killJvm.sh";

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

            ResultInfoSet resultInfos = saltStackService.doCommand(new MinionList(targes), stopCommandPath);

            if (resultInfos.getInfoList().size() == 1) {
                ResultInfo resultInfo = resultInfos.get(0);
                Map<String, Object> results = resultInfo.getResults();
                for (Map.Entry<String, Object> entry : results.entrySet()) {

                    if (entry.getValue().equals("")) {


                    } else {
                        throw new SaltStackException(entry.getValue().toString());
                    }
                }
            } else {
                return false;
            }

        }

        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
