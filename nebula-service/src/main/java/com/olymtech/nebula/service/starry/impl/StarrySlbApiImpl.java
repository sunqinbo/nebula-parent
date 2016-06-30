/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.starry.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.cdn.model.v20141111.DescribeRefreshTasksResponse;
import com.aliyuncs.slb.model.v20140515.DescribeHealthStatusResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerAttributeResponse;
import com.olymtech.nebula.common.utils.HttpUtils;
import com.olymtech.nebula.entity.NebulaPublishSlb;
import com.olymtech.nebula.service.starry.IStarrySlbApi;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Gavin on 2016-02-03 13:03.
 */
@Service
public class StarrySlbApiImpl implements IStarrySlbApi {

    private Logger logger   =   LoggerFactory.getLogger(this.getClass());

    @Value("${starry.api.url}")
    private String starryApiUrl;

    @Override
    public DescribeLoadBalancerAttributeResponse describeLoadBalancerAttribute(NebulaPublishSlb publishSlb){
        String describe_loadbalancer_attribute_api = starryApiUrl+"/slb/describeLoadBalancerAttribute.htm?";
        String url = describe_loadbalancer_attribute_api+"account="+publishSlb.getAliyunAccount()+"&regionId="+publishSlb.getRegionId()+"&loadBalancerId="+publishSlb.getLoadBalancerId();
        try{
            String jsonDataString = HttpUtils.getResponesEncodeUTF8ByURL(url, "post");
            JSONObject jsonObject = JSONObject.parseObject(jsonDataString);
            if (null == jsonObject) {
                logger.error("[ERROR]describeLoadBalancerAttribute jsonObject null:"+url);
                return null;
            }
            if(!jsonObject.get("callbackMsg").toString().equals("Success")){
                logger.error("[ERROR]describeLoadBalancerAttribute callbackMsg is not Success:"+url);
                return null;
            }
            JSONObject jsonObjectData = jsonObject.getJSONObject("responseContext");
            if(jsonObjectData == null){
                logger.error("[ERROR]describeLoadBalancerAttribute responseContext is null:"+url);
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            DescribeLoadBalancerAttributeResponse describeLoadBalancerAttributeResponse = mapper.readValue(jsonObjectData.toJSONString(),DescribeLoadBalancerAttributeResponse.class);
            return describeLoadBalancerAttributeResponse;
        }catch (Exception e){
            logger.error("describeLoadBalancerAttribute error:",e);
        }
        return null;
    }

    @Override
    public DescribeHealthStatusResponse describeHealthStatusTasks(NebulaPublishSlb publishSlb){
        String describe_healthstatus_tasks_api = starryApiUrl+"/slb/describeHealthStatusTasks.htm?";
        String url = describe_healthstatus_tasks_api+"account="+publishSlb.getAliyunAccount()+"&regionId="+publishSlb.getRegionId()+"&loadBalancerId="+publishSlb.getLoadBalancerId();
        try{
            String jsonDataString = HttpUtils.getResponesEncodeUTF8ByURL(url, "post");
            JSONObject jsonObject = JSONObject.parseObject(jsonDataString);
            if (null == jsonObject) {
                logger.error("[ERROR]describeHealthStatusTasks jsonObject is null:"+url);
                return null;
            }
            if(!jsonObject.get("callbackMsg").toString().equals("Success")){
                logger.error("[ERROR]describeHealthStatusTasks callbackMsg is not Success:"+url);
                return null;
            }
            JSONObject jsonObjectData = jsonObject.getJSONObject("responseContext");
            if(jsonObjectData == null){
                logger.error("[ERROR]describeHealthStatusTasks responseContext is null:"+url);
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            DescribeHealthStatusResponse describeHealthStatusResponse = mapper.readValue(jsonObjectData.toJSONString(),DescribeHealthStatusResponse.class);
            return describeHealthStatusResponse;
        }catch (Exception e){
            logger.error("describeHealthStatusTasks error:",e);
        }
        return null;
    }


}
