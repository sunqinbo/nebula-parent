/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NebulaPublishApp;

import java.util.List;

/**
 * Created by Gavin on 2015-11-04 17:23.
 */
public interface IPublishAppService {

    public List<NebulaPublishApp> selectByEventIdAndModuleId(Integer eventId, Integer moduleId);

    public void deleteByEventId(Integer eventId);

    public List<NebulaPublishApp> selectByEventId(Integer eventId);
}
