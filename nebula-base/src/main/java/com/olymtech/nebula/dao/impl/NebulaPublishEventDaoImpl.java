/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaPublishEventDao;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gavin on 2015-10-23 14:28.
 */
@Repository("nebulaPublishEventDao")
public class NebulaPublishEventDaoImpl extends BaseDaoImpl<NebulaPublishEvent,Integer> implements INebulaPublishEventDao {

    @Override
    public List<NebulaPublishEvent> selectAllPagingWithUser(NebulaPublishEvent event) {
        return getSqlSession().selectList(CLASS_NAME + "-Select-All-Paging-With-User-Where", event);
    }

    @Override
    public int selectCountWithUser(NebulaPublishEvent event){
        return (Integer) getSqlSession().selectOne(CLASS_NAME + "-Select-Count-With-User-Where", event);
    }

}
