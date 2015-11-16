/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.NebulaPublishHost;

/**
 * Created by Gavin on 2015-10-23 14:20.
 */
public interface INebulaPublishHostDao extends IBaseDao<NebulaPublishHost,Integer> {

    public void deleteByEventId(Integer eventId);

}
