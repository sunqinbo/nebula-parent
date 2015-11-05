/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishAppDao;
import com.olymtech.nebula.entity.NebulaPublishApp;
import com.olymtech.nebula.service.IPublishAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin on 2015-11-04 17:24.
 */
@Service("publishAppService")
public class PublishAppServiceImpl implements IPublishAppService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private INebulaPublishAppDao nebulaPublishAppDao;

    @Override
    public List<NebulaPublishApp> selectByEventIdAndModuleId(Integer eventId, Integer moduleId){
        List<NebulaPublishApp> nebulaPublishApps = new ArrayList<>();
        try{
            NebulaPublishApp nebulaPublishApp = new NebulaPublishApp();
            nebulaPublishApp.setPublishEventId(eventId);
            nebulaPublishApp.setPublishModuleId(moduleId);
            nebulaPublishApps = nebulaPublishAppDao.selectAllPaging(nebulaPublishApp);
        }catch (Exception e){
            logger.error("selectByEventIdAndModuleId error:",e);
        }
        return nebulaPublishApps;
    }



}
