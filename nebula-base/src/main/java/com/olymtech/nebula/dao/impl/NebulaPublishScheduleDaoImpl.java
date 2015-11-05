/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaPublishScheduleDao;
import com.olymtech.nebula.entity.NebulaPublishSchedule;
import org.springframework.stereotype.Repository;

/**
 * Created by Gavin on 2015-10-23 14:32.
 */
@Repository("nebulaPublishScheduleDao")
public class NebulaPublishScheduleDaoImpl extends BaseDaoImpl<NebulaPublishSchedule,Integer> implements INebulaPublishScheduleDao {
}
