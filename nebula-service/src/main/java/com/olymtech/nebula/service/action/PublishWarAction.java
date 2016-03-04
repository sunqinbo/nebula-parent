/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.salt.ISaltStackService;
import com.olymtech.nebula.entity.NebulaPublishApp;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishHost;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.file.verify.IFileVerifyService;
import com.olymtech.nebula.service.IFileReadService;
import com.olymtech.nebula.service.IPublishAppService;
import com.olymtech.nebula.service.IPublishHostService;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.olymtech.nebula.common.utils.DataConvert;
import com.suse.saltstack.netapi.results.ResultInfo;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taoshanchang 15/11/5
 */

@Service
public class PublishWarAction extends AbstractAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IPublishAppService publishAppService;

    @Autowired
    private ISaltStackService saltStackService;

    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Autowired
    private IPublishHostService publishHostService;

    @Autowired
    private IFileVerifyService fileVerifyService;

    @Value("${base_war_dir}")
    private String BaseWarDir;
    @Value("${base_etc_dir}")
    private String BaseEtcDir;
    @Value("${master_deploy_dir}")
    private String MasterDeployDir;
    @Value("${master_id}")
    private String MasterId;


    public PublishWarAction() {
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_WAR, event.getPublishActionGroup(), null, "");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targets = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targets.add(nebulaPublishHost.getPassPublishHostIp());
            }

            String warFromBase = event.getPublishProductKey() + "/publish_war/";

            List<NebulaPublishApp> appList = publishAppService.selectByEventIdAndModuleId(event.getId(), publishModule.getId());

            int AppSuccessCount = 0;
            for (NebulaPublishApp app : appList) {

                ResultInfoSet result = saltStackService.cpFileRemote(targets, warFromBase + app.getPublishAppName() + ".war", BaseWarDir + publishModule.getPublishModuleKey() + "/" + app.getPublishAppName() + ".war");

                ResultInfo resultInfo = result.get(0);
                Map<String, Object> results = resultInfo.getResults();
                int i = 0;
                int successCount = 0;
                for (Map.Entry<String, Object> entry : results.entrySet()) {
                    NebulaPublishHost nebulaPublishHost = publishHosts.get(i++);
                    nebulaPublishHost.setActionGroup(PublishActionGroup.PRE_MINION);
                    nebulaPublishHost.setActionName(PublishAction.PUBLISH_NEW_WAR);
                    nebulaPublishHost.setActionResult(entry.getValue().toString());
                    nebulaPublishHost.setIsSuccessAction(true);//TODO 暂时这里返回的都是salt执行成功的，因为返回的数据没有标准化，后期处理
                    publishHostService.updatePublishHost(nebulaPublishHost);
                    successCount++;
                }
                if (successCount != targets.size()) {
                    return false;
                }else{
                    AppSuccessCount++;
                }
            }
            if (AppSuccessCount != appList.size()) {
                publishScheduleService.logScheduleByAction(
                        event.getId(),
                        PublishAction.PUBLISH_NEW_WAR,
                        event.getPublishActionGroup(),
                        false,
                        "实际分发war包成功主机数:" + AppSuccessCount + ",  目标分发war包主机数:" + targets.size()
                );
                return false;
            }
        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_WAR, event.getPublishActionGroup(), null, "All models and sub targes 'execute' success");
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

            String masterDir = MasterDeployDir + event.getPublishProductKey() + "/publish_war";
            String minionDir = BaseWarDir + publishModule.getPublishModuleKey();

//            Map<String,String> masterHost = fileVerifyService.checkFilesMd5ByDir(masterDir,".war");

            /** 这里其实是一个 */
            List<String> targetsMaster = new ArrayList<String>();
            targetsMaster.add(MasterId);

            ResultInfoSet minionResult = saltStackService.checkFilesMd5ByDir(targets, minionDir, ".war");
            ResultInfoSet masterResult = saltStackService.checkFilesMd5ByDir(targetsMaster, masterDir, ".war");

            ResultInfo minionResultInfo = minionResult.get(0);
            Map<String, Object> minionResults = minionResultInfo.getResults();

            ResultInfo masterResultInfo = masterResult.get(0);
            Map<String, Object> masterResults = masterResultInfo.getResults();

            int i = 0;
            Map<String,Map<String,String>> masterFileMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : masterResults.entrySet()) {
                String jsonString = entry.getValue().toString();
                Map<String,String> everyHost = DataConvert.jsonStringToList(jsonString);
                everyHost = DataConvert.fileMapWithoutModuleKey(everyHost,masterDir);

                if (everyHost.size() == 0) {
                    logger.error("PublishWarAction doCheck: Get master war dir faild, sting:"+jsonString);
                }else{
                    masterFileMap.put(MasterId,everyHost);
                }
            }

            if (masterFileMap.size() != targetsMaster.size()) {
                publishScheduleService.logScheduleByAction(
                        event.getId(),
                        PublishAction.PUBLISH_NEW_WAR,
                        event.getPublishActionGroup(),
                        false,
                        "获取master war包md5数据异常。");
                return false;
            }

            i = 0;
            /** Map<host,Map<filename,md5>> */
            Map<String,Map<String,String>> minionFileMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : minionResults.entrySet()) {
                NebulaPublishHost nebulaPublishHost = publishHosts.get(i++);
                nebulaPublishHost.setActionGroup(PublishActionGroup.PRE_MINION);
                nebulaPublishHost.setActionName(PublishAction.PUBLISH_NEW_WAR);
                String jsonString = entry.getValue().toString();
                Map<String,String> everyHost = DataConvert.jsonStringToList(jsonString);
                everyHost = DataConvert.fileMapWithoutModuleKey(everyHost,minionDir);

                if (everyHost.size() == 0) {
                    nebulaPublishHost.setActionResult(nebulaPublishHost.getActionResult()+"<br>获取分发war包完整性校验数据并解析失败。返回数据："+jsonString);
                    nebulaPublishHost.setIsSuccessAction(false);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                } else {
                    nebulaPublishHost.setActionResult(nebulaPublishHost.getActionResult()+"<br>获取分发war包完整性校验数据并解析成功。");
                    nebulaPublishHost.setIsSuccessAction(true);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                    minionFileMap.put(nebulaPublishHost.getPassPublishHostIp(),everyHost);
                }
            }

            if (minionFileMap.size() != targets.size()) {
                publishScheduleService.logScheduleByAction(
                        event.getId(),
                        PublishAction.PUBLISH_NEW_WAR,
                        event.getPublishActionGroup(),
                        false,
                        "获取minion war包md5数据异常。实际获取数据主机数：" + minionFileMap.size() + ",  目标主机数数:" + targets.size());
                return false;
            }

            List<NebulaPublishApp> appList = publishAppService.selectByEventIdAndModuleId(event.getId(), publishModule.getId());


            Map<String,String> masterHost = masterFileMap.get(MasterId);
            for (NebulaPublishApp app : appList) {
                /** 需要校验的文件 */
                String filename = app.getPublishAppName()+".war";
                /** master上md5 */
                String masterMd5 = masterHost.get(filename);

                /** 多台minion循环 */
                for (Map.Entry<String, Map<String,String>> entryEvent : minionFileMap.entrySet()) {
                    String ip = entryEvent.getKey();
                    /** 单台minion数据 */
                    Map<String,String> minionEveryHost = entryEvent.getValue();
                    /** minion上md5 */
                    String minionMd5 = minionEveryHost.get(filename);

                    if( !masterMd5.equals(minionMd5) ){
                        publishScheduleService.logScheduleByAction(
                                event.getId(),
                                PublishAction.PUBLISH_NEW_WAR,
                                event.getPublishActionGroup(),
                                false,
                                "分发war包完整性校验数据不一致，文件名：'"+filename+"' 被发布机ip：" + ip + " 被发布机文件md5:" + minionMd5 + ", master文件md5：" + masterMd5);
                        return false;
                    }
                }

            }

        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.PUBLISH_NEW_WAR, event.getPublishActionGroup(), true, "All models and sub targes 'execute and check' success");
        return true;
    }

}
