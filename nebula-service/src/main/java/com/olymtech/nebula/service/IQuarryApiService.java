/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NebulaPublishEvent;

/**
 * Created by Gavin on 2016-05-27 13:23.
 */
public interface IQuarryApiService {
    Boolean notifyDeployEndToQuarry(NebulaPublishEvent event);
}
