/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaPublishHostDao;
import com.olymtech.nebula.entity.NebulaPublishHost;
import org.springframework.stereotype.Repository;

/**
 * Created by Gavin on 2015-10-23 14:31.
 */
@Repository("nebulaPublishHostDao")
public class NebulaPublishHostDaoImpl extends BaseDaoImpl<NebulaPublishHost,Integer> implements INebulaPublishHostDao {

    @Override
    public void deleteByEventId(Integer eventId){
        getSqlSession().delete(CLASS_NAME + "-Delete-By-Event-Id", eventId);
    }
}
