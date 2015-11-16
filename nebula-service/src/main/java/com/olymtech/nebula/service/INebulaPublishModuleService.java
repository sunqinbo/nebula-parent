/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NebulaPublishModule;

import java.util.List;

/**
 * Created by Gavin on 2015-11-06 19:33.
 */
public interface INebulaPublishModuleService {

    public List<NebulaPublishModule> selectByEventId(Integer eventId);

    public void deleteByEventId(Integer eventId);
}
