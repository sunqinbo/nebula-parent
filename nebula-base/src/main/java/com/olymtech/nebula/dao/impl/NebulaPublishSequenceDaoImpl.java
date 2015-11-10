/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaPublishSequenceDao;
import com.olymtech.nebula.entity.NebulaPublishSequence;
import org.springframework.stereotype.Repository;

/**
 * Created by Gavin on 2015-11-02 14:13.
 */
@Repository("nebulaPublishSequenceDao")
public class NebulaPublishSequenceDaoImpl extends BaseDaoImpl<NebulaPublishSequence,Integer> implements INebulaPublishSequenceDao {

    @Override
    public NebulaPublishSequence selectByActionName(String actionname) {
        return getSqlSession().selectOne(CLASS_NAME + "-Select-By-ActionName",actionname);
    }
}
