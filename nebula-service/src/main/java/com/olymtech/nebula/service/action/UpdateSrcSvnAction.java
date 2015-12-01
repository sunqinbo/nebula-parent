/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.svn.SvnUtils;
import com.olymtech.nebula.entity.NebulaPublishApp;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.olymtech.nebula.entity.NebulaPublishSvnBaseline;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.entity.enums.PublishEnv;
import com.olymtech.nebula.file.analyze.IFileAnalyzeService;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.olymtech.nebula.service.IPublishSvnBaselineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.wc.SVNClientManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Autowired
    private IFileAnalyzeService fileAnalyzeService;

    @Value("${master_deploy_dir}")
    private String MasterDeployDir;
    @Value("${src_svn_username}")
    private String SrcSvnUsername;
    @Value("${src_svn_password}")
    private String SrcSvnPassword;

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.UPDATE_SRC_SVN, event.getPublishActionGroup(), null, "");

        String svnUrl = event.getProductSrcSvn()+"/"+event.getPublishEnv();
        SVNClientManager svnClientManager = SvnUtils.createSvnClientManager(svnUrl, SrcSvnUsername, SrcSvnPassword);
        try{

            /** 如果为 生产发布，需要将发布的war包上传到源svn，所以需要复制到src_svn目录下 */
            if(event.getPublishEnv().equals(PublishEnv.product.toString())){
                String publishWarPath = MasterDeployDir + event.getPublishProductKey()+"/publish_war/";
                String srcSvnPath = MasterDeployDir + event.getPublishProductKey()+"/src_svn/";
                for(NebulaPublishModule publishModule:event.getPublishModules()){
                    String moduleName = publishModule.getPublishModuleName();
                    for(NebulaPublishApp publishApp:publishModule.getPublishApps()) {
                        String fileName = publishApp.getPublishAppName()+".war";

                        String srcFilePath = publishWarPath + "/" + fileName;
                        String destFilePath = srcSvnPath + moduleName + "/" + fileName;
                        Boolean copyResult = fileAnalyzeService.copyFile(srcFilePath, destFilePath);
                        if(!copyResult){
                            publishScheduleService.logScheduleByAction(event.getId(), PublishAction.UPDATE_SRC_SVN, event.getPublishActionGroup(), false, "copy publish war file error:"+fileName);
                            return false;
                        }
                    }
                }
            }

            /** svn提交 */
            String commitMessage = "";
            SVNCommitInfo commitInfo = SvnUtils.commit(svnClientManager,MasterDeployDir + event.getPublishProductKey() + "/src_svn/",false,commitMessage);

            if(commitInfo != null){
                String commitCallback = commitInfo.toString();

                Pattern pattern = Pattern.compile("^r\\d+ by ");
                Matcher matcher = pattern.matcher(commitCallback);

                /**
                 * 提交成功：返回版本号 r3 by 'superman' at Wed Nov 18 15:18:51 CST 2015
                 * 空提交：返回 EMPTY COMMIT
                 */
                if(matcher.find() || commitCallback.equals("EMPTY COMMIT")){
                    publishScheduleService.logScheduleByAction(event.getId(), PublishAction.UPDATE_SRC_SVN, event.getPublishActionGroup(), true, commitInfo.toString());

                    /** 记录 svn base */
                    NebulaPublishSvnBaseline publishSvnBaseline = new NebulaPublishSvnBaseline(event.getPublishProductName(),event.getPublishEnv(),event.getProductSrcSvn(),event.getId(),commitCallback);
                    publishSvnBaselineService.insertAndUpdate(publishSvnBaseline);
                    return true;
                }
            }
        }catch (Exception e){
            logger.error("UpdateSrcSvnAction error:",e);
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.UPDATE_SRC_SVN, event.getPublishActionGroup(), false, "svn commit error");
        return false;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }
}
