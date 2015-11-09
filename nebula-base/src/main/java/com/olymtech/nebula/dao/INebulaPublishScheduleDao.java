/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.NebulaPublishSchedule;

import java.util.List;

/**
 * Created by Gavin on 2015-10-23 14:23.
 */
public interface INebulaPublishScheduleDao extends IBaseDao<NebulaPublishSchedule,Integer> {

    public List<NebulaPublishSchedule> selectByEventId(Integer eventId);

}
