/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaPublishModuleDao;
import com.olymtech.nebula.entity.NebulaPublishModule;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gavin on 2015-10-23 14:31.
 */
@Repository("nebulaPublishModuleDao")
public class NebulaPublishModuleDaoImpl extends BaseDaoImpl<NebulaPublishModule,Integer> implements INebulaPublishModuleDao {

    @Override
    public List<NebulaPublishModule> selectModulesByEventId(Integer publishEventId){
        return getSqlSession().selectList(CLASS_NAME + "-Select-By-PublishEventId", publishEventId);
    }

    @Override
    public void deleteByEventId(Integer eventId){
        getSqlSession().delete(CLASS_NAME + "-Delete-By-Event-Id", eventId);
    }

}
