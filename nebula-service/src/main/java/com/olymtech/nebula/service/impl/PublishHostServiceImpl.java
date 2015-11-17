/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishHostDao;
import com.olymtech.nebula.entity.NebulaPublishHost;
import com.olymtech.nebula.service.IPublishHostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gavin on 2015-11-04 18:06.
 */
@Service("publishHostService")
public class PublishHostServiceImpl implements IPublishHostService {

    @Resource
    private INebulaPublishHostDao nebulaPublishHostDao;

    @Override
    public List<NebulaPublishHost> selectByEventIdAndModuleId(Integer eventId,Integer moduleId){
        NebulaPublishHost nebulaPublishHost = new NebulaPublishHost();
        nebulaPublishHost.setPublishEventId(eventId);
        nebulaPublishHost.setPublishModuleId(moduleId);
        return nebulaPublishHostDao.selectAllPaging(nebulaPublishHost);
    }

    @Override
    public void deleteByEventId(Integer eventId){
        nebulaPublishHostDao.deleteByEventId(eventId);
    }

    @Override
    public int createPublishHost(NebulaPublishHost publicHost) {
        return nebulaPublishHostDao.insert(publicHost);
    }
}
