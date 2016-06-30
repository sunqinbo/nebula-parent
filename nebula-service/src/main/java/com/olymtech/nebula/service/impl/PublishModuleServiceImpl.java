/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishAppDao;
import com.olymtech.nebula.dao.INebulaPublishModuleDao;
import com.olymtech.nebula.entity.NebulaPublishApp;
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
    @Resource
    INebulaPublishAppDao nebulaPublishAppDao;

    @Override
    public List<NebulaPublishModule> selectByEventId(Integer eventId) {
        List<NebulaPublishModule> publishModules = nebulaPublishModuleDao.selectModulesByEventId(eventId);
        for (NebulaPublishModule module : publishModules) {
            List<NebulaPublishApp> publishApps = nebulaPublishAppDao.selectByModuleId(module.getId());
            module.setPublishApps(publishApps);
        }
        return publishModules;
    }

    @Override
    public void deleteByEventId(Integer eventId) {
        nebulaPublishModuleDao.deleteByEventId(eventId);
    }

    @Override
    public void update(NebulaPublishModule publishModule){
        nebulaPublishModuleDao.update(publishModule);
    }

    @Override
    public void updateByIdSelective(NebulaPublishModule publishModule){
        nebulaPublishModuleDao.updateByIdSelective(publishModule);
    }

}
