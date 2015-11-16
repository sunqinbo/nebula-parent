/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.NebulaPublishModule;

import java.util.List;

/**
 * Created by Gavin on 2015-10-23 14:23.
 */
public interface INebulaPublishModuleDao extends IBaseDao<NebulaPublishModule,Integer> {

    public List<NebulaPublishModule> selectModulesByEventId(Integer publishEventId);


    public void deleteByEventId(Integer eventId);
}
