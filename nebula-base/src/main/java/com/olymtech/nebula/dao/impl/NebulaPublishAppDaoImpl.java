/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaPublishAppDao;
import com.olymtech.nebula.entity.NebulaPublishApp;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gavin on 2015-11-03 15:43.
 */
@Repository("nebulaPublishAppDao")
public class NebulaPublishAppDaoImpl extends BaseDaoImpl<NebulaPublishApp, Integer> implements INebulaPublishAppDao {

    @Override
    public void deleteByEventId(Integer eventId) {
        getSqlSession().delete(CLASS_NAME + "-Delete-By-Event-Id", eventId);
    }

    @Override
    public List<NebulaPublishApp> selectByEventId(Integer eventId) {
        return getSqlSession().selectList(CLASS_NAME + "-Select-By-Event-Id", eventId);
    }

    @Override
    public List<NebulaPublishApp> selectByModuleId(Integer moduleId) {
        return getSqlSession().selectList(CLASS_NAME + "-Select-By-Module-Id", moduleId);
    }


}
