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
import com.olymtech.nebula.service.IPublishBaseService;
import com.olymtech.nebula.service.IPublishHostService;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.suse.saltstack.netapi.exception.SaltStackException;
import com.suse.saltstack.netapi.results.ResultInfo;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taoshanchang 15/11/4
 */
@Service
public class CpEtcAction extends AbstractAction {

    @Autowired
    private IPublishBaseService publishBaseService;

    @Autowired
    private ISaltStackService saltStackService;

    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Autowired
    private IPublishHostService publishHostService;

    @Value("${base_etc_dir}")
    private String BaseEtcDir;

    public CpEtcAction() {
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.COPY_PUBLISH_OLD_ETC, PublishActionGroup.PRE_MINION, null ,"");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targes = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targes.add(nebulaPublishHost.getPassPublishHostIp());
            }

            String oldModule =  publishBaseService.selectLastModuleKeyByPublishEvent(event, publishModule.getPublishModuleName());

            if (oldModule==null){
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.COPY_PUBLISH_OLD_ETC,PublishActionGroup.PRE_MINION, true, "");
                continue;
            }

            HashMap<String, String> map = new HashMap<String, String>();
            map.put(BaseEtcDir + oldModule + "/*", BaseEtcDir + publishModule.getPublishModuleKey());

            ResultInfoSet result = saltStackService.cpDir(new SaltTarget(targes), map);

            if (result.getInfoList().size() == 1) {
                ResultInfo resultInfo = result.get(0);
                Map<String, Object> results = resultInfo.getResults();
                int i = 0;
                for (Map.Entry<String, Object> entry : results.entrySet()) {
                    NebulaPublishHost nebulaPublishHost = publishHosts.get(i);
                    nebulaPublishHost.setActionGroup(PublishActionGroup.PRE_MINION);
                    nebulaPublishHost.setActionName(PublishAction.COPY_PUBLISH_OLD_ETC);
                    if (entry.getValue().equals("")) {
                        nebulaPublishHost.setActionResult("success");
                        nebulaPublishHost.setIsSuccessAction(true);
                        publishHostService.updatePublishHost(nebulaPublishHost);
                    } else {
                        nebulaPublishHost.setActionResult(entry.getValue().toString());
                        nebulaPublishHost.setIsSuccessAction(false);
                        publishHostService.updatePublishHost(nebulaPublishHost);
                        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.COPY_PUBLISH_OLD_ETC,PublishActionGroup.PRE_MINION, false, "error message");
                        throw new SaltStackException(entry.getValue().toString());
                    }
                }
            } else {
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.COPY_PUBLISH_OLD_ETC,PublishActionGroup.PRE_MINION, false, "error message");
                return false;
            }
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.COPY_PUBLISH_OLD_ETC,PublishActionGroup.PRE_MINION, true, "");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
