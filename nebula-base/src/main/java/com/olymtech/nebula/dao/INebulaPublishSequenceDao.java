/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.dao.IBaseDao;
import com.olymtech.nebula.entity.NebulaPublishSequence;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;

import java.util.List;

/**
 * Created by Gavin on 2015-11-02 14:12.
 */
public interface INebulaPublishSequenceDao extends IBaseDao<NebulaPublishSequence,Integer> {
    public NebulaPublishSequence selectByActionName(PublishAction actionName);

    public List<NebulaPublishSequence> selectByActionGroup(PublishActionGroup publishActionGroup);
}
