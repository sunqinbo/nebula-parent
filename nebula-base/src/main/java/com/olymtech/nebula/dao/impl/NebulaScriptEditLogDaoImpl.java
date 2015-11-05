/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaScriptEditLogDao;
import com.olymtech.nebula.entity.NebulaScriptEditLog;
import org.springframework.stereotype.Repository;

/**
 * Created by Gavin on 2015-10-23 14:35.
 */
@Repository("nebulaScriptEditLogDao")
public class NebulaScriptEditLogDaoImpl extends BaseDaoImpl<NebulaScriptEditLog,Integer> implements INebulaScriptEditLogDao {
}
