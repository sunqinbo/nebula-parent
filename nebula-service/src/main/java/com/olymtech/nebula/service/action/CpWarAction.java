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
import com.olymtech.nebula.service.IPublishBaseService;
import com.olymtech.nebula.service.IPublishHostService;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.olymtech.nebula.common.utils.DataConvert;
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
public class CpWarAction extends AbstractAction {

    @Autowired
    private IPublishBaseService publishBaseService;

    @Autowired
    private ISaltStackService saltStackService;

    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Autowired
    private IPublishHostService publishHostService;

    @Value("${base_war_dir}")
    private String BaseWarDir;

    public CpWarAction() {
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.COPY_PUBLISH_OLD_WAR, event.getPublishActionGroup(), null, "");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targets = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targets.add(nebulaPublishHost.getPassPublishHostIp());
            }

            String oldModule = publishBaseService.selectLastModuleKeyByPublishEvent(event, publishModule.getPublishModuleName());
            if (oldModule == null) {
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.COPY_PUBLISH_OLD_ETC, event.getPublishActionGroup(), true, "");
                continue;
            }

            Map<String, String> fileMap = new HashMap<String, String>();
            fileMap.put(BaseWarDir + oldModule + "/*.war", BaseWarDir + publishModule.getPublishModuleKey());

            Map<String, String> dirMap = new HashMap<String, String>();
            dirMap.put(BaseWarDir + oldModule + "/ROOT/", BaseWarDir + publishModule.getPublishModuleKey());

            ResultInfoSet result = saltStackService.cpFileAndDir(targets, fileMap, dirMap);

            ResultInfo resultInfo = result.get(0);
            Map<String, Object> results = resultInfo.getResults();
            int i = 0;
            int successCount = 0;
            for (Map.Entry<String, Object> entry : results.entrySet()) {
                NebulaPublishHost nebulaPublishHost = publishHosts.get(i++);
                nebulaPublishHost.setActionGroup(PublishActionGroup.PRE_MINION);
                nebulaPublishHost.setActionName(PublishAction.COPY_PUBLISH_OLD_WAR);
                if (entry.getValue().equals("")) {
                    nebulaPublishHost.setActionResult("success");
                    nebulaPublishHost.setIsSuccessAction(true);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                    successCount++;
                } else {
                    nebulaPublishHost.setActionResult(entry.getValue().toString());
                    nebulaPublishHost.setIsSuccessAction(false);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                }
            }
            if (successCount != targets.size()) {
                publishScheduleService.logScheduleByAction(
                        event.getId(),
                        PublishAction.COPY_PUBLISH_OLD_WAR,
                        event.getPublishActionGroup(),
                        false,
                        "执行拷贝war异常。成功数:" + successCount + ",  目标成功数:" + targets.size());
                return false;
            }
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.COPY_PUBLISH_OLD_WAR, event.getPublishActionGroup(), null, "All models and sub targes 'execute' success");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }

    @Override
    public boolean doCheck(NebulaPublishEvent event) throws Exception {
        List<NebulaPublishModule> publishModules = event.getPublishModules();
        for (NebulaPublishModule publishModule : publishModules) {
            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targets = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targets.add(nebulaPublishHost.getPassPublishHostIp());
            }

            String oldModule = publishBaseService.selectLastModuleKeyByPublishEvent(event, publishModule.getPublishModuleName());
            if (oldModule == null) {
                publishScheduleService.logScheduleByAction(event.getId(), PublishAction.COPY_PUBLISH_OLD_ETC, event.getPublishActionGroup(), true, "");
                continue;
            }

            String newDir = BaseWarDir + publishModule.getPublishModuleKey();
            String oldDir = BaseWarDir + oldModule;

            ResultInfoSet newResult = saltStackService.checkFilesMd5ByDir(targets, newDir, ".war");
            ResultInfoSet oldResult = saltStackService.checkFilesMd5ByDir(targets, oldDir, ".war");

            ResultInfo newResultInfo = newResult.get(0);
            Map<String, Object> newResults = newResultInfo.getResults();

            ResultInfo oldResultInfo = oldResult.get(0);
            Map<String, Object> oldResults = oldResultInfo.getResults();

            int i = 0;
            /** Map<host,Map<filename,md5>> */
            Map<String,Map<String,String>> newFileMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : newResults.entrySet()) {
                NebulaPublishHost nebulaPublishHost = publishHosts.get(i++);
                nebulaPublishHost.setActionGroup(PublishActionGroup.PRE_MINION);
                nebulaPublishHost.setActionName(PublishAction.COPY_PUBLISH_OLD_WAR);
                String jsonString = entry.getValue().toString();
                Map<String,String> everyHost = DataConvert.fileJsonStringToList(jsonString);
                everyHost = DataConvert.fileMapWithoutModuleKey(everyHost,newDir);

                if (everyHost.size() == 0) {
                    nebulaPublishHost.setActionResult("校验文件时，解析脚本数据失败。脚本返回数据："+jsonString);
                    nebulaPublishHost.setIsSuccessAction(false);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                } else {
                    nebulaPublishHost.setActionResult("success");
                    nebulaPublishHost.setIsSuccessAction(true);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                    newFileMap.put(nebulaPublishHost.getPassPublishHostIp(),everyHost);
                }
            }

            if (newFileMap.size() != targets.size()) {
                publishScheduleService.logScheduleByAction(
                        event.getId(),
                        PublishAction.COPY_PUBLISH_OLD_WAR,
                        event.getPublishActionGroup(),
                        false,
                        "校验文件时，获取'新目录'数据异常。成功数：" + newFileMap.size() + ",  目标成功数:" + targets.size());
                return false;
            }

            i = 0;
            /** Map<host,Map<filename,md5>> */
            Map<String,Map<String,String>> oldFileMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : oldResults.entrySet()) {
                NebulaPublishHost nebulaPublishHost = publishHosts.get(i++);
                nebulaPublishHost.setActionGroup(PublishActionGroup.PRE_MINION);
                nebulaPublishHost.setActionName(PublishAction.COPY_PUBLISH_OLD_WAR);
                String jsonString = entry.getValue().toString();
                Map<String,String> everyHost = DataConvert.fileJsonStringToList(jsonString);
                everyHost = DataConvert.fileMapWithoutModuleKey(everyHost,oldDir);

                if (everyHost.size() == 0) {
                    nebulaPublishHost.setActionResult("校验文件时，解析脚本数据失败。脚本返回数据："+jsonString);
                    nebulaPublishHost.setIsSuccessAction(false);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                } else {
                    nebulaPublishHost.setActionResult("success");
                    nebulaPublishHost.setIsSuccessAction(true);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                    oldFileMap.put(nebulaPublishHost.getPassPublishHostIp(),everyHost);
                }
            }

            if (oldFileMap.size() != targets.size()) {
                publishScheduleService.logScheduleByAction(
                        event.getId(),
                        PublishAction.COPY_PUBLISH_OLD_WAR,
                        event.getPublishActionGroup(),
                        false,
                        "校验文件时，获取'旧目录'数据异常。成功数：" + oldFileMap.size() + ",  目标成功数:" + targets.size());
                return false;
            }

            /** 比对 newFileMap oldFileMap */
            for (Map.Entry<String, Map<String,String>> entry : newFileMap.entrySet()) {
                String ip = entry.getKey();
                Map<String,String> newEveryHost = entry.getValue();
                Map<String,String> oldEveryHost = oldFileMap.get(ip);

                /** 拷贝数量异常 */
                if(newEveryHost.size() != oldEveryHost.size()){
                    publishScheduleService.logScheduleByAction(
                            event.getId(),
                            PublishAction.COPY_PUBLISH_OLD_WAR,
                            event.getPublishActionGroup(),
                            false,
                            "校验文件时，文件拷贝个数异常。主机ip：" + ip + " 新目录文件个数：" + newEveryHost.size() + ",  旧目录文件个数:" + oldEveryHost.size());
                    return false;
                }

                /** 拷贝md5异常 */
                for (Map.Entry<String, String> entryEvent : newEveryHost.entrySet()){
                    String filename = entryEvent.getKey();
                    String newMd5 = entryEvent.getValue();
                    String oldMd5 = oldEveryHost.get(filename);
                    if( !newMd5.equals(oldMd5) ){
                        publishScheduleService.logScheduleByAction(
                                event.getId(),
                                PublishAction.COPY_PUBLISH_OLD_WAR,
                                event.getPublishActionGroup(),
                                false,
                                "校验文件时，文件拷贝'"+filename+"'md5异常。主机ip：" + ip + " 新目录文件md5：" + newMd5 + ",  旧目录文件md5:" + oldMd5);
                        return false;
                    }
                }
            }

        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.COPY_PUBLISH_OLD_WAR, event.getPublishActionGroup(), true, "All models and sub targes 'execute and check' success");
        return true;
    }
}
