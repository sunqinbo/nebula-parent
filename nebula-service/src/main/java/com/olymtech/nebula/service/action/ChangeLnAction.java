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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taoshanchang 15/11/6
 */
@Service
public class ChangeLnAction extends AbstractAction {

    @Autowired
    private ISaltStackService saltStackService;
    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Value("${base_war_dir}")
    private String BaseWarDir;
    @Value("${base_etc_dir}")
    private String BaseEtcDir;
    @Value("${etc_link}")
    private String EtcLink;
    @Value("${war_link}")
    private String WarLink;

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CHANGE_LN, PublishActionGroup.PUBLISH_REAL, null, "");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targes = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targes.add(nebulaPublishHost.getPassPublishHostIp());
            }

            HashMap<String, String> lnMap = new HashMap<String, String>();
            lnMap.put(BaseWarDir + publishModule.getPublishModuleKey(), WarLink);
            lnMap.put(BaseEtcDir + publishModule.getPublishModuleKey(), EtcLink);
            ResultInfoSet result = saltStackService.makeLn(new MinionList(targes), lnMap);

            if (result.getInfoList().size() == 1) {
                ResultInfo resultInfo = result.get(0);
                Map<String, Object> results = resultInfo.getResults();
                for (Map.Entry<String, Object> entry : results.entrySet()) {
                    if (entry.getValue().equals("")) {
                        //todo 每台机子的执行信息处理
                    } else {
                        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CHANGE_LN, PublishActionGroup.PUBLISH_REAL, false, "error message");
                        throw new SaltStackException(entry.getValue().toString());
                    }
                }

            } else {
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CHANGE_LN, PublishActionGroup.PUBLISH_REAL, false, "error message");
                return false;
            }

        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CHANGE_LN, PublishActionGroup.PUBLISH_REAL, true, "");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
