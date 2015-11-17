/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NebulaPublishHost;

import java.util.List;

/**
 * Created by Gavin on 2015-11-04 18:05.
 */
public interface IPublishHostService {

    public List<NebulaPublishHost> selectByEventIdAndModuleId(Integer eventId, Integer moduleId);

    public void deleteByEventId(Integer eventId);

    public int createPublishHost(NebulaPublishHost publicHost);
}
