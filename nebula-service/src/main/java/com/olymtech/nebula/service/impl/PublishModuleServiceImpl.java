/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishModuleDao;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.olymtech.nebula.service.INebulaPublishModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gavin on 2015-11-06 19:33.
 */
@Service("nebulaPublishModuleService")
public class PublishModuleServiceImpl implements INebulaPublishModuleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    INebulaPublishModuleDao nebulaPublishModuleDao;

    @Override
    public List<NebulaPublishModule> selectByEventId(Integer eventId){
        return nebulaPublishModuleDao.selectModulesByEventId(eventId);
    }

    @Override
    public void deleteByEventId(Integer eventId){
        nebulaPublishModuleDao.deleteByEventId(eventId);
    }

}
