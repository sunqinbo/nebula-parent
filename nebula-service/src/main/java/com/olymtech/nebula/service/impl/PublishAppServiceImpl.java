/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishAppDao;
import com.olymtech.nebula.entity.NebulaPublishApp;
import com.olymtech.nebula.service.IPublishAppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gavin on 2015-11-04 17:24.
 */
@Service("publishAppService")
public class PublishAppServiceImpl implements IPublishAppService {
    @Resource
    private INebulaPublishAppDao nebulaPublishAppDao;

    @Override
    public List<NebulaPublishApp> selectByEventIdAndModuleId(Integer eventId, Integer moduleId){
        NebulaPublishApp nebulaPublishApp = new NebulaPublishApp();
        nebulaPublishApp.setPublishEventId(eventId);
        nebulaPublishApp.setPublishModuleId(moduleId);
        return nebulaPublishAppDao.selectAllPaging(nebulaPublishApp);
    }



}
