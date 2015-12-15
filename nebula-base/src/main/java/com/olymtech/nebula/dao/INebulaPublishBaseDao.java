/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.NebulaPublishBase;

/**
 * Created by Gavin on 2015-11-03 15:43.
 */
public interface INebulaPublishBaseDao extends IBaseDao<NebulaPublishBase,Integer> {

    public void cleanBaseByEventId(Integer eventId);

}
