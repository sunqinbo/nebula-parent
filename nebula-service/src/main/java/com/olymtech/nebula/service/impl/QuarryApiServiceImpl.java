/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.olymtech.nebula.common.utils.DateUtils;
import com.olymtech.nebula.common.utils.HttpUtils;
import com.olymtech.nebula.common.utils.MD5Utils;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.service.IQuarryApiService;
import com.olymtech.nebula.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gavin on 2016-05-27 13:23.
 */
@Service
public class QuarryApiServiceImpl implements IQuarryApiService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IUserService userService;

    /**
     * diamond ops.properties	com.olymtech.ops.common
     */
    @Value("${quarry.web.api.url}")
    private String quarryWebUrl;

    @Value("${web.auth.username}")
    private String quarryWebUserName;

    /**
     * 通知 quarry，部署完成
     * @param event
     * @return
     */
    @Override
    public Boolean notifyDeployEndToQuarry(NebulaPublishEvent event){
        String jsonDataString = "";
        try{
            /**
             * 如果quarry rid为空，表示不需要通知。
             */
            if(StringUtils.isEmpty(event.getQuarryRid())){
                return true;
            }

            NebulaUserInfo userInfo = userService.findByUsername(quarryWebUserName);
            if(userInfo == null){
                logger.error("notifyDeployEndToQuarry error: username:"+quarryWebUserName+" is not in db.");
                return false;
            }
            String token = getToken(userInfo);
            String url = quarryWebUrl + "/webApi/callbackForNebula";
            Map<String, String> map = new HashMap<>();
            map.put("username", quarryWebUserName);
            map.put("token", token);
            map.put("rid", event.getQuarryRid());
            jsonDataString = HttpUtils.postRequestWithPostBody(url, map, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(jsonDataString);
            if (null == jsonObject) {
                //"请求不成功,返回null"
                logger.error("notifyDeployEndToQuarry error: jsonObject is null");
                return false;
            }
            if (!jsonObject.get("callbackMsg").toString().equals("Success")) {
                //"请求失败,返回null"
                logger.error("notifyDeployEndToQuarry error: callbackMsg is not Success");
                return false;
            }
            return true;
        }catch (Exception e){
            logger.error("notifyDeployEndToQuarry error: jsonDataString is "+jsonDataString,e);
        }
        return false;
    }

    private String getToken(NebulaUserInfo userInfo) {
        String dateTime = DateUtils.longDateSimple(new Date());
        return MD5Utils.encode(userInfo.getUsername() + userInfo.getPassword() + userInfo.getSecurityKey() + dateTime);
    }

}
