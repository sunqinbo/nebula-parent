/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.svn.SvnUtils;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishSvnBaseline;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.olymtech.nebula.service.IPublishSvnBaselineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.wc.SVNClientManager;

/**
 * Created by Gavin on 2015-11-18 10:34.
 */
@Service
public class UpdateSrcSvnAction extends AbstractAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IPublishScheduleService publishScheduleService;
    @Autowired
    private IPublishSvnBaselineService publishSvnBaselineService;

    @Value("${master_deploy_dir}")
    private String MasterDeployDir;
    @Value("${src_svn_username}")
    private String SrcSvnUsername;
    @Value("${src_svn_password}")
    private String SrcSvnPassword;

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.UPDATE_SRC_SVN, PublishActionGroup.SUCCESS_END, null, "");
        String svnUrl = event.getProductSrcSvn()+"/"+event.getPublishEnv();
        SVNClientManager svnClientManager = SvnUtils.createSvnClientManager(svnUrl, SrcSvnUsername, SrcSvnPassword);
        try{
            String commitMessage = "";
            SVNCommitInfo commitInfo = SvnUtils.commit(svnClientManager,MasterDeployDir + event.getPublishProductKey() + "/src_svn/",false,commitMessage);

            if(commitInfo != null){
                String commitCallback = commitInfo.toString();
                if(commitCallback.equals("EMPTY COMMIT")){
                    logger.error(MasterDeployDir + event.getPublishProductKey() + "/src_svn/"+"  is empty commit");
                }else{
                    publishScheduleService.logScheduleByAction(event.getId(), PublishAction.UPDATE_SRC_SVN, PublishActionGroup.SUCCESS_END, true, commitInfo.toString());

                    /** 记录 svn base */
                    NebulaPublishSvnBaseline publishSvnBaseline = new NebulaPublishSvnBaseline(event.getPublishProductName(),event.getPublishEnv(),event.getProductSrcSvn(),event.getId(),commitCallback);
                    publishSvnBaselineService.insertAndUpdate(publishSvnBaseline);
                    return true;
                }
            }
        }catch (Exception e){
            logger.error("UpdateSrcSvnAction error:",e);
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.UPDATE_SRC_SVN, PublishActionGroup.SUCCESS_END, false, "commit error");
        return false;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
