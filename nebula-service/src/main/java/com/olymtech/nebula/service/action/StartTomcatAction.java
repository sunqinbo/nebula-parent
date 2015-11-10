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
public class StartTomcatAction extends AbstractAction {

    @Autowired
    private ISaltStackService saltStackService;
    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Value("${start_command_path}")
    private String startCommandPath;
    @Value("${stop_command_path}")
    private String stopCommandPath;

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.START_TOMCAT, PublishActionGroup.PUBLISH_REAL, null, "");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targes = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targes.add(nebulaPublishHost.getPassPublishHostIp());
            }

            List<String> pathList = new ArrayList<String>();
            pathList.add(stopCommandPath);
            pathList.add(startCommandPath);

            ResultInfoSet resultInfos = saltStackService.doCommand(new MinionList(targes), pathList);

            if (resultInfos.getInfoList().size() == 1) {
                ResultInfo resultInfo = resultInfos.get(0);
                Map<String, Object> results = resultInfo.getResults();
                for (Map.Entry<String, Object> entry : results.entrySet()) {

                    if (entry.getValue().equals("")) {

                        //todo 每台机子的执行信息处理
                    } else {
                        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.START_TOMCAT, PublishActionGroup.PUBLISH_REAL, false, "error message");
                        throw new SaltStackException(entry.getValue().toString());
                    }
                }
            } else {
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.START_TOMCAT, PublishActionGroup.PUBLISH_REAL, false, "error message");
                return false;
            }

        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.START_TOMCAT, PublishActionGroup.PUBLISH_REAL, true, "");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
