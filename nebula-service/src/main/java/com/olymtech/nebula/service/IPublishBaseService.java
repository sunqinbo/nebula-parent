/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NebulaPublishEvent;

/**
 * Created by Gavin on 2015-11-05 15:49.
 */
public interface IPublishBaseService {

    public String selectLastModuleKeyByPublishEvent(NebulaPublishEvent event, String moduleName);
}
