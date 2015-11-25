/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.NebulaPublishApp;

import java.util.List;

/**
 * Created by Gavin on 2015-11-03 15:42.
 */
public interface INebulaPublishAppDao extends IBaseDao<NebulaPublishApp, Integer> {

    public void deleteByEventId(Integer eventId);

    public List<NebulaPublishApp> selectByEventId(Integer eventId);

    public List<NebulaPublishApp> selectByModuleId(Integer moduleId);
}
