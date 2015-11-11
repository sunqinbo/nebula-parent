/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaPublishSequenceDao;
import com.olymtech.nebula.entity.NebulaPublishSequence;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gavin on 2015-11-02 14:13.
 */
@Repository("nebulaPublishSequenceDao")
public class NebulaPublishSequenceDaoImpl extends BaseDaoImpl<NebulaPublishSequence,Integer> implements INebulaPublishSequenceDao {

    @Override
    public NebulaPublishSequence selectByActionName(PublishAction actionName) {
        return getSqlSession().selectOne(CLASS_NAME + "-Select-By-ActionName",actionName);
    }

    @Override
    public List<NebulaPublishSequence> selectByActionGroup(PublishActionGroup actionGroup){
        return getSqlSession().selectList(CLASS_NAME + "-Select-By-ActionGroup", actionGroup);
    }

}
