/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.salt.ISaltStackService;
import com.olymtech.nebula.core.salt.core.SaltTarget;
import com.olymtech.nebula.entity.NebulaPublishApp;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishHost;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.IPublishAppService;
import com.olymtech.nebula.service.IPublishHostService;
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
public class PublishWarAction extends AbstractAction {

    @Autowired
    private IPublishAppService publishAppService;

    @Autowired
    private ISaltStackService saltStackService;

    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Autowired
    private IPublishHostService publishHostService;

    @Value("${base_war_dir}")
    private String BaseWarDir;
    @Value("${base_etc_dir}")
    private String BaseEtcDir;
    @Value("${master_deploy_dir}")
    private String MasterWarDir;


    public PublishWarAction() {
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_WAR, event.getPublishActionGroup(), null, "");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targes = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targes.add(nebulaPublishHost.getPassPublishHostIp());
            }

            String warFromBase = event.getPublishProductKey() + "/publish_war/";

            List<NebulaPublishApp> appList = publishAppService.selectByEventIdAndModuleId(event.getId(), publishModule.getId());

            for (NebulaPublishApp app : appList) {

                ResultInfoSet result = saltStackService.cpFileRemote(new SaltTarget(targes), warFromBase + app.getPublishAppName()+".war", BaseWarDir + publishModule.getPublishModuleKey()+"/"+app.getPublishAppName()+".war");

                if (result.getInfoList().size() == 1) {
                    ResultInfo resultInfo = result.get(0);
                    Map<String, Object> results = resultInfo.getResults();
                    int i = 0;
                    for (Map.Entry<String, Object> entry : results.entrySet()) {
                        NebulaPublishHost nebulaPublishHost = publishHosts.get(i++);
                        nebulaPublishHost.setActionGroup(PublishActionGroup.PRE_MINION);
                        nebulaPublishHost.setActionName(PublishAction.PUBLISH_NEW_WAR);
                        nebulaPublishHost.setActionResult(entry.getValue().toString());
                        nebulaPublishHost.setIsSuccessAction(true);//TODO 暂时这里返回的都是salt执行成功的，因为返回的数据没有标准化，后期处理
                        publishHostService.updatePublishHost(nebulaPublishHost);
                        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_WAR,event.getPublishActionGroup(), false, "error message");
                    }
                } else {
                    publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_WAR,event.getPublishActionGroup(), false, "error message");
                    return false;
                }
            }
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_WAR,event.getPublishActionGroup(), true, "");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }

}
