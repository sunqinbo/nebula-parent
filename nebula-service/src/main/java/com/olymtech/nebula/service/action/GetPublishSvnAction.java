/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.svn.SvnUtils;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.IPublishScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.wc.SVNClientManager;

/**
 * Created by Gavin on 2015-11-04 19:31.
 */
@Service
public class GetPublishSvnAction extends AbstractAction {

    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Value("${master_deploy_dir}")
    private String MasterDeployDir;
    @Value("${dev_svn_username}")
    private String DevSvnUsername;
    @Value("${dev_svn_password}")
    private String DevSvnPassword;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public GetPublishSvnAction(){
        super();
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.GET_PUBLISH_SVN, event.getPublishActionGroup(), null, "");
        String svnUrl = event.getPublishSvn();
        SVNClientManager svnClientManager = SvnUtils.createSvnClientManager(svnUrl, DevSvnUsername, DevSvnPassword);
        try{
            Boolean svnResult = SvnUtils.checkout(svnClientManager, svnUrl, MasterDeployDir + event.getPublishProductKey() + "/publish_war/");
            if(svnResult){
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.GET_PUBLISH_SVN, event.getPublishActionGroup(), true, "");
                return true;
            }
        }catch (Exception e){
            logger.error("GetPublishSvnAction error:",e);
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.GET_PUBLISH_SVN, event.getPublishActionGroup(), false, "获取发布war包时发生错误，可点击“重试”，尝试再次拉去。");
        return false;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }

    @Override
    public boolean doCheck(NebulaPublishEvent event) throws Exception {
        return true;
    }

}

