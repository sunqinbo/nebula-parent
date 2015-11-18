/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.salt.ISaltStackService;
import com.olymtech.nebula.core.salt.core.SaltTarget;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishHost;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.IPublishHostService;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfo;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author taoshanchang 15/11/6
 */
@Service
public class StopTomcatAction extends AbstractAction {
    @Autowired
    private ISaltStackService saltStackService;
    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Autowired
    private IPublishHostService publishHostService;

    @Value("${stop_command_path}")
    private String stopCommandPath;

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.STOP_TOMCAT, event.getPublishActionGroup(), null, "");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targes = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targes.add(nebulaPublishHost.getPassPublishHostIp());
            }

            List<String> pathList = new ArrayList<String>();
            pathList.add(stopCommandPath);

            ResultInfoSet resultInfos = saltStackService.doCommand(new SaltTarget(targes), pathList);

            if (resultInfos.getInfoList().size() == 1) {
                ResultInfo resultInfo = resultInfos.get(0);
                Map<String, Object> results = resultInfo.getResults();
                int i = 0;
                for (Map.Entry<String, Object> entry : results.entrySet()) {
                    NebulaPublishHost nebulaPublishHost = publishHosts.get(i++);
                    nebulaPublishHost.setActionGroup(PublishActionGroup.PUBLISH_REAL);
                    nebulaPublishHost.setActionName(PublishAction.STOP_TOMCAT);
                    if (entry.getValue().equals("")) {
                        nebulaPublishHost.setActionResult("success");
                        nebulaPublishHost.setIsSuccessAction(true);
                        publishHostService.updatePublishHost(nebulaPublishHost);
                    } else {
                        nebulaPublishHost.setActionResult(entry.getValue().toString());
                        nebulaPublishHost.setIsSuccessAction(false);
                        publishHostService.updatePublishHost(nebulaPublishHost);
                        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.STOP_TOMCAT, event.getPublishActionGroup(), false, "error message");
                        throw new SaltStackException(entry.getValue().toString());
                    }
                }
            } else {
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.STOP_TOMCAT, event.getPublishActionGroup(), false, "error message");
                return false;
            }

        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.STOP_TOMCAT, event.getPublishActionGroup(), true, "");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
