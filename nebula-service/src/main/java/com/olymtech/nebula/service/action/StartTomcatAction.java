/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.service.action;

import com.olymtech.nebula.common.utils.DataConvert;
import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.salt.ISaltStackService;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishHost;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.IPublishHostService;
import com.olymtech.nebula.service.IPublishScheduleService;
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
 * @author taoshanchang 15/11/6
 */
@Service
public class StartTomcatAction extends AbstractAction {

    @Autowired
    private ISaltStackService saltStackService;
    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Autowired
    private IPublishHostService publishHostService;

    @Value("${start_command_path}")
    private String startCommandPath;
    @Value("${stop_command_path}")
    private String stopCommandPath;

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.START_TOMCAT, event.getPublishActionGroup(), null, "");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targets = new ArrayList<String>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targets.add(nebulaPublishHost.getPassPublishHostIp());
            }

            List<String> pathList = new ArrayList<String>();
            pathList.add(stopCommandPath);
            pathList.add(startCommandPath);

            ResultInfoSet resultInfos = saltStackService.doCommand(targets, pathList);

            ResultInfo resultInfo = resultInfos.get(0);
            Map<String, Object> results = resultInfo.getResults();
            int i = 0;
            int successCount = 0;
            for (Map.Entry<String, Object> entry : results.entrySet()) {
                NebulaPublishHost nebulaPublishHost = publishHosts.get(i++);
                nebulaPublishHost.setActionGroup(PublishActionGroup.PUBLISH_REAL);
                nebulaPublishHost.setActionName(PublishAction.START_TOMCAT);
                nebulaPublishHost.setActionResult(entry.getValue().toString());
                nebulaPublishHost.setIsSuccessAction(true);//TODO 暂时这里返回的都是salt执行成功的，因为返回的数据没有标准化，后期处理
                publishHostService.updatePublishHost(nebulaPublishHost);
                successCount++;
            }
            if (successCount != targets.size()) {
                publishScheduleService.logScheduleByAction(
                        event.getId(),
                        PublishAction.START_TOMCAT,
                        event.getPublishActionGroup(),
                        false,
                        "实际成功启动tomcat主机数：" + successCount + ", 目标启动tomcat主机数:" + targets.size()
                );
                return false;
            }

        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.START_TOMCAT, event.getPublishActionGroup(), null, "All models and sub targes 'execute' success");
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {

    }

    @Override
    public boolean doCheck(NebulaPublishEvent event) throws Exception {
        Thread.sleep(3000);
        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {
            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            List<String> targets = new ArrayList<String>();
            /** Map<ip:NebulaPublishHost> */
            Map<String,NebulaPublishHost> hostMap = new HashMap<>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                targets.add(nebulaPublishHost.getPassPublishHostIp());
                hostMap.put(nebulaPublishHost.getPassPublishHostIp(),nebulaPublishHost);
            }

            ResultInfoSet minionResult = saltStackService.checkTomcat(targets);

            ResultInfo minionResultInfo = minionResult.get(0);
            Map<String, Object> minionResults = minionResultInfo.getResults();

            int i = 0;
            Map<String,Map<String,String>> minionMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : minionResults.entrySet()) {
                NebulaPublishHost nebulaPublishHost = publishHosts.get(i++);
                nebulaPublishHost.setActionGroup(PublishActionGroup.PUBLISH_REAL);
                nebulaPublishHost.setActionName(PublishAction.START_TOMCAT);
                String jsonString = entry.getValue().toString();
                Map<String,String> everyHost = DataConvert.jsonStringToList(jsonString);

                if (everyHost.size() == 0) {
                    nebulaPublishHost.setActionResult(nebulaPublishHost.getActionResult()+"<br>获得进程校验数据并解析失败。返回数据："+jsonString);
                    nebulaPublishHost.setIsSuccessAction(false);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                } else {
                    nebulaPublishHost.setActionResult(nebulaPublishHost.getActionResult()+"<br>获得进程校验数据并解析成功。");
                    nebulaPublishHost.setIsSuccessAction(true);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                    minionMap.put(nebulaPublishHost.getPassPublishHostIp(),everyHost);
                }
            }

            if (minionMap.size() != targets.size()) {
                publishScheduleService.logScheduleByAction(
                        event.getId(),
                        PublishAction.START_TOMCAT,
                        event.getPublishActionGroup(),
                        false,
                        "校验进程时，获取数据异常。实际成功数：" + minionMap.size() + ",  目标成功数:" + targets.size());
                return false;
            }

            int successCount = 0;
            for (Map.Entry<String, Map<String,String>> entry : minionMap.entrySet()) {
                String ip = entry.getKey();
                NebulaPublishHost nebulaPublishHost = hostMap.get(ip);
                /**
                 * 数据格式：
                 * {"Port8080": false, "JavaCount": 0, "Ip": "172.16.137.130"}
                 */
                Map<String,String> everyHost = entry.getValue();

                String portCheckString = everyHost.get("Port8080");
                String javaCountCheckString = everyHost.get("JavaCount");

                Boolean portCheck = false;
                Integer javaCountCheck = 0;
                try{
                    portCheck = Boolean.valueOf(portCheckString);
                    javaCountCheck = Integer.parseInt(javaCountCheckString);
                }catch (Exception e){

                }

                /**
                 * java进程不等于1，8080端口没有开，判断为异常
                 */
                if(javaCountCheck != 1){
                    String portStatus = portCheck?"开启":"关闭";
                    nebulaPublishHost.setActionResult(nebulaPublishHost.getActionResult()+"<br>校验进程时错误，java进程个数："+javaCountCheck+" ,8080端口状态："+portStatus);
                    nebulaPublishHost.setIsSuccessAction(false);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                }else{
                    nebulaPublishHost.setActionResult(nebulaPublishHost.getActionResult()+"<br>校验进程成功。");
                    nebulaPublishHost.setIsSuccessAction(true);
                    publishHostService.updatePublishHost(nebulaPublishHost);
                    successCount++;
                }
            }

            if (successCount != targets.size()) {
                publishScheduleService.logScheduleByAction(
                        event.getId(),
                        PublishAction.START_TOMCAT,
                        event.getPublishActionGroup(),
                        false,
                        "校验进程时，校验数据异常。成功数：" + successCount + ",  目标成功数:" + targets.size());
                return false;
            }

        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.START_TOMCAT, event.getPublishActionGroup(), true, "All models and sub targes 'execute and check' success");
        return true;
    }
}
