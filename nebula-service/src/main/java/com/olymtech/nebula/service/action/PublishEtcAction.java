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
import com.olymtech.nebula.service.IPublishAppService;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.suse.saltstack.netapi.results.ResultInfo;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author taoshanchang 15/11/5
 */

@Service
public class PublishEtcAction extends AbstractAction {

    @Autowired
    private IPublishAppService publishAppService;

    @Autowired
    private ISaltStackService saltStackService;

    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Value("${base_war_dir}")
    private String BaseWarDir;
    @Value("${base_etc_dir}")
    private String BaseEtcDir;
    @Value("${master_deploy_dir}")
    private String MasterWarDir;


    public PublishEtcAction() {
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_ETC, PublishActionGroup.PRE_MINION, null, "");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targes = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targes.add(nebulaPublishHost.getPassPublishHostIp());
            }

            String etcFrom = event.getPublishProductKey() + "/src_svn/etc";

            ResultInfoSet result = saltStackService.cpDirRemote(new SaltTarget(targes), etcFrom, BaseEtcDir + publishModule.getPublishModuleKey());

            if (result.getInfoList().size() == 1) {
                ResultInfo resultInfo = result.get(0);
                Map<String, Object> results = resultInfo.getResults();
                for (Map.Entry<String, Object> entry : results.entrySet()) {
                    //if (entry.getValue().equals("")) {
                    //    //todo 每台机子的执行信息处理
                    //} else {
                    //    publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_ETC, PublishActionGroup.PRE_MINION, false, "error message");
                    //    throw new SaltStackException(entry.getValue().toString());
                    //}
                }
            } else {
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_ETC, PublishActionGroup.PRE_MINION, false, "error message");
                return false;
            }
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_ETC,PublishActionGroup.PRE_MINION, true, "");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }

}
