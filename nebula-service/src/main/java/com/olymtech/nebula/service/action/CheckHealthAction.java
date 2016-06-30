/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.action;

import com.aliyuncs.slb.model.v20140515.DescribeHealthStatusResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerAttributeResponse;
import com.olymtech.nebula.core.action.AbstractAction;
import com.olymtech.nebula.core.salt.ISaltStackService;
import com.olymtech.nebula.dao.INebulaPublishModuleDao;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.entity.enums.HostPublishStatus;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.INginxService;
import com.olymtech.nebula.service.IPublishHostService;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.olymtech.nebula.service.IPublishSlbService;
import com.olymtech.nebula.service.starry.IStarrySlbApi;
import com.suse.saltstack.netapi.results.ResultInfo;
import com.suse.saltstack.netapi.results.ResultInfoSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gavin on 2016-06-23 09:22.
 */
@Service
public class CheckHealthAction extends AbstractAction {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IPublishSlbService publishSlbService;
    @Autowired
    private IPublishScheduleService publishScheduleService;
    @Autowired
    private INebulaPublishModuleDao publishModuleDao;
    @Resource
    private IStarrySlbApi starrySlbApi;
    @Autowired
    private INginxService nginxService;

    @Value("${check_health_timeout}")
    private String checkHealthTimeout;

    @Override
    public boolean doAction(NebulaPublishEvent event) throws Exception {
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CHECK_HEALTH, event.getPublishActionGroup(), null, "");

        List<NebulaPublishModule> publishModules = event.getPublishModules();

        for (NebulaPublishModule publishModule : publishModules) {
            if(event.getNowBatchTag()<=publishModule.getBatchTotal()){
                /** 更新当前批次 */
                publishModule.setNowBatchTag(event.getNowBatchTag());
                publishModuleDao.updateByIdSelective(publishModule);
            }else{
                continue;
            }

            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            /** 当前批次机器列表 */
            List<NebulaPublishHost> targetHosts = new ArrayList<>();
//            Map<String,NebulaPublishHost> targetHostsMap = new HashMap<>();
            for (NebulaPublishHost nebulaPublishHost : publishHosts) {
                if(nebulaPublishHost.getBatchTag().equals(event.getNowBatchTag())){
                    targetHosts.add(nebulaPublishHost);
//                    targetHostsMap.put(nebulaPublishHost.getPassPublishHostIp(), nebulaPublishHost);
                }
            }

            Integer time = 30;

            if(checkHealthTimeout != null){
                time = Integer.parseInt(checkHealthTimeout);
            }

            long startTime = System.currentTimeMillis();
            Boolean result;
            do{
                long endTime = System.currentTimeMillis(); //获取结束时间
                if ((endTime - startTime) / 1000 > time) {
                    publishScheduleService.logScheduleByAction(
                            event.getId(),
                            PublishAction.CHECK_HEALTH,
                            event.getPublishActionGroup(),
                            false,
                            time+"秒内，健康检查失败，应用未恢复正常，发布终止。"
                    );
                    return false;
                }
                result = checkHealthAllHost(targetHosts,event,publishModule);
                Thread.sleep(5000);
            }while (!result);

        }
        publishScheduleService.logScheduleByAction(event.getId(), PublishAction.CHECK_HEALTH, event.getPublishActionGroup(), true, "All check health 'execute' success");
        return true;
    }

    /**
     * 检测slb状态，和nginx状态
     * 1.slb没有，则认为ok
     * 2.nginx没有，则认为ok
     * 3.有，则需要检测状态
     * @param targetHosts
     * @param event
     * @return
     */
    public Boolean checkHealthAllHost(List<NebulaPublishHost> targetHosts,NebulaPublishEvent event,NebulaPublishModule module){

        try{
            List<NebulaPublishSlb> publishSlbs = publishSlbService.selectByPublishEventIdAndModuleId(event.getId(), module.getId());

            Map<String,NebulaPublishHost> hostIPMap = new HashMap<>();
            Map<String,NebulaPublishHost> hostInstanceIdMap = new HashMap<>();
            for(NebulaPublishHost publishHost :targetHosts){
                hostIPMap.put(publishHost.getPassPublishHostIp(),publishHost);
                hostInstanceIdMap.put(publishHost.getHostInstanceId(),publishHost);
            }

            /**
             * 检测slb状态
             */
            for (NebulaPublishSlb publishSlb : publishSlbs) {
                DescribeLoadBalancerAttributeResponse loadBalancerAttributeResponse = starrySlbApi.describeLoadBalancerAttribute(publishSlb);
                DescribeHealthStatusResponse describeHealthStatusResponse = starrySlbApi.describeHealthStatusTasks(publishSlb);
                String loadBalancerStatus = loadBalancerAttributeResponse.getLoadBalancerStatus();

                /** 判断slb状态 */
                if(!loadBalancerStatus.equals("active")){
                    return false;
                }

                /** 判断后端服务器状态 */
                for(DescribeHealthStatusResponse.BackendServer backendServer:describeHealthStatusResponse.getBackendServers()){
                    /** 实例id － 被发布机，暂无对应关系；只能通过匹配InstanceId，查看 */
                    NebulaPublishHost host = hostInstanceIdMap.get(backendServer.getServerId());
                    if(host != null && !backendServer.getServerHealthStatus().equals("normal")){
                        return false;
                    }
                }
            }

            /**
             * 检测nginx状态
             */
            List<NginxServer> nginxServers;
            if(event.getPublishEnv().equals("test1")||event.getPublishEnv().equals("test2")||event.getPublishEnv().equals("test3")||event.getPublishEnv().equals("stage")){
                nginxServers = nginxService.getStageNginxServers();
            }else{
                nginxServers = nginxService.getProductNginxServers();
            }

            for(NginxServer nginxServer:nginxServers){
                String ip = nginxServer.getName().split(":")[0];
                NebulaPublishHost host = hostIPMap.get(ip);
                if(host != null && !nginxServer.getStatus().equals("up")){
                    return false;
                }
            }
            return true;
        }catch (Exception e){
            logger.error("checkHealthAllHost error:",e);
            return false;
        }
    }

    @Override
    public void doFailure(NebulaPublishEvent event) throws Exception {

    }

    @Override
    public boolean doCheck(NebulaPublishEvent event) throws Exception {
        return true;
    }

}
