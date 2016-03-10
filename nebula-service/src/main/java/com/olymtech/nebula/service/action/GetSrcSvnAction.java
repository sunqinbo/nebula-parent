/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.svn.SvnUtils;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.file.analyze.IFileAnalyzeService;
import com.olymtech.nebula.service.IPublishScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.wc.SVNClientManager;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by Gavin on 2015-11-04 20:12.
 */
@Service
public class GetSrcSvnAction extends AbstractAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Resource
    private IFileAnalyzeService fileAnalyzeService;

    @Value("${master_deploy_dir}")
    private String MasterDeployDir;
    @Value("${src_svn_username}")
    private String SrcSvnUsername;
    @Value("${src_svn_password}")
    private String SrcSvnPassword;

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.GET_SRC_SVN, event.getPublishActionGroup(), null, "");
        String svnUrl = event.getProductSrcSvn()+"/"+event.getPublishEnv();
        SVNClientManager svnClientManager = SvnUtils.createSvnClientManager(svnUrl, SrcSvnUsername, SrcSvnPassword);
        try{
            Boolean svnResult = SvnUtils.checkout(svnClientManager, svnUrl, MasterDeployDir + event.getPublishProductKey() + "/src_svn/");
            if(svnResult){
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.GET_SRC_SVN, event.getPublishActionGroup(), true, "");
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.UPDATE_ETC, event.getPublishActionGroup(), null, "");

                 /*GetSrcSvnAction完成后拷贝一份etc(/home/saas/deploy_tmp/<productKey>/src_svn/etc）目录到 /home/saas/deploy_tmp/<productKey>/src_etc*/
                String dirSrcPath = MasterDeployDir + event.getPublishProductKey() + "/src_svn/etc";
                String destPath = MasterDeployDir + event.getPublishProductKey() + "/src_etc";
                File src = new File(dirSrcPath);
                File dest = new File(destPath);
                fileAnalyzeService.copyFolder(src, dest);

                return true;
            }
        }catch (Exception e){
            logger.error("GetPublishSvnAction error:",e);
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.GET_SRC_SVN, event.getPublishActionGroup(), false, "error message");
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
