/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.svn.SvnUtils;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.service.IPublishScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.wc.SVNClientManager;

/**
 * Created by Gavin on 2015-11-04 19:31.
 */
@Service
public class GetPublishSvnAction extends AbstractAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public GetPublishSvnAction(){
        super();
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        String svnUrl = event.getPublishSvn();
        SVNClientManager svnClientManager = SvnUtils.createSvnClientManager(svnUrl, "gavin", "hellomonitor");
        try{
            SvnUtils.checkout(svnClientManager,svnUrl,"/Users/saas/deploy_tmp/" + event.getPublishProductKey() + "/publish_war/" );
            publishScheduleService.logScheduleByAction(event.getId(), PublishAction.GET_SRC_SVN, true, "");
            return true;
        }catch (Exception e){
            logger.error("GetPublishSvnAction error:",e);
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.GET_SRC_SVN, false, "error message");
        return false;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }

}

