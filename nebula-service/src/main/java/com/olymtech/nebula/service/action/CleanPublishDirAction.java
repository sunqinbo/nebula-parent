/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.entity.enums.PublishStatus;
import com.olymtech.nebula.service.IPublishEventService;
import com.olymtech.nebula.service.IPublishScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by Gavin on 2015-11-18 11:15.
 */
@Service
public class CleanPublishDirAction extends AbstractAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IPublishScheduleService publishScheduleService;
    @Autowired
    private IPublishEventService publishEventService;

    @Value("${master_deploy_dir}")
    private String MasterDeployDir;

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        /**
         * 清理发布目录最后一批次执行，之前批次不执行
         */
        if(!event.getNowBatchTag().equals(event.getBatchTotal())){
            return true;
        }

        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CLEAN_PUBLISH_DIR, PublishActionGroup.CLEAN_END, null, "");
        String localPath = MasterDeployDir + event.getPublishProductKey();
        File tmpDir = new File(localPath);
        try{
            Boolean result = deleteDir(tmpDir);
            if(result){

                /** 更新错误数,发布状态 */
                if(event.getPublishActionGroup() == PublishActionGroup.SUCCESS_END){
                    publishEventService.updateLogCountSum(true, PublishStatus.PUBLISHED, event);
                }else if(event.getPublishActionGroup() == PublishActionGroup.FAIL_END){
                    publishEventService.updateLogCountSum(false, PublishStatus.ROLLBACK, event);
                }else if(event.getPublishActionGroup() == PublishActionGroup.CANCEL_END){
                    event.setIsSuccessPublish(false);
                    event.setPublishStatus(PublishStatus.CANCEL);
                    publishEventService.update(event);
                }

                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CLEAN_PUBLISH_DIR, PublishActionGroup.CLEAN_END, true, "清除发布目录成功");
                return true;
            }
        }catch (Exception e){
            logger.error("CleanPublishDirAction error:",e);
        }

        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CLEAN_PUBLISH_DIR, PublishActionGroup.CLEAN_END, false, "清除发布目录失败："+localPath);
        return false;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }

    @Override
    public boolean doCheck(NebulaPublishEvent event) throws Exception {
        return true;
    }

    private Boolean deleteDir(File dir){
        if(dir.isDirectory()){
            String[] child = dir.list();
            for(int i = 0;i<child.length;i++){
                Boolean success = deleteDir(new File(dir,child[i]));
                if(!success){
                    return false;
                }
            }
        }
        return dir.delete();
    }

}
