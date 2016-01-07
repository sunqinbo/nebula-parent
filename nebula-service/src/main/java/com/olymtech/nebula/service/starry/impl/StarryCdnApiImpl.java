/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.starry.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.cdn.model.v20141111.DescribeRefreshTasksResponse;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesResponse;
import com.olymtech.nebula.common.utils.HttpUtils;
import com.olymtech.nebula.service.starry.IStarryCdnApi;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Gavin on 2016-01-06 18:02.
 */
@Service
public class StarryCdnApiImpl implements IStarryCdnApi {

    private Logger logger   =   LoggerFactory.getLogger(this.getClass());

    @Value("${starry.api.url}")
    private String starryApiUrl;

    /**
     * 获取cdn刷新列表
     * */
    @Override
    public DescribeRefreshTasksResponse describeRefreshTasks(String aliyunAcount, String regionId){
        String describe_refresh_tasks_api = starryApiUrl+"/cdn/describeRefreshTasks.htm?";
        String url = describe_refresh_tasks_api+"account="+aliyunAcount+"&regionId="+regionId;
        try{
            String jsonDataString = HttpUtils.getResponesEncodeUTF8ByURL(url,"post");
            JSONObject jsonObject = JSONObject.parseObject(jsonDataString);
            if (null == jsonObject) {
                return null;
            }
            if(!jsonObject.get("callbackMsg").toString().equals("Success")){
                return null;
            }
            JSONObject jsonObjectData = jsonObject.getJSONObject("responseContext");
            if(jsonObjectData == null){
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            DescribeRefreshTasksResponse describeRefreshTasksResponse = mapper.readValue(jsonObjectData.toJSONString(),DescribeRefreshTasksResponse.class);
            return describeRefreshTasksResponse;
        }catch (Exception e){
            logger.error("describeRefreshTasksResponse error:",e);
        }
        return null;
    }

    /**
     * 刷新一个cdn域名
     * */
    @Override
    public RefreshObjectCachesResponse refreshObjectCaches(String aliyunAcount, String regionId, String objectPath, String objectType){
        String describe_refresh_object_api = starryApiUrl+"/cdn/describeRefreshObject.htm?";
        String url = describe_refresh_object_api+"account="+aliyunAcount+"&regionId="+regionId+"&objectPath="+objectPath+"&objectType="+objectType;
        try{
            String jsonDataString = HttpUtils.getResponesEncodeUTF8ByURL(url,"post");
            JSONObject jsonObject = JSONObject.parseObject(jsonDataString);
            if (null == jsonObject) {
                return null;
            }
            if(!jsonObject.get("callbackMsg").toString().equals("Success")){
                return null;
            }
            JSONObject jsonObjectData = jsonObject.getJSONObject("responseContext");
            if(jsonObjectData == null){
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            RefreshObjectCachesResponse refreshObjectCachesResponse = mapper.readValue(jsonObjectData.toJSONString(),RefreshObjectCachesResponse.class);
            return refreshObjectCachesResponse;
        }catch (Exception e){
            logger.error("describeRefreshTasksResponse error:",e);
        }
        return null;
    }

}
