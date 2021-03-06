/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaScriptDao;
import com.olymtech.nebula.entity.NebulaScript;
import org.springframework.stereotype.Repository;

/**
 * Created by Gavin on 2015-10-23 14:34.
 */
@Repository("nebulaScriptDao")
public class NebulaScriptDaoImpl extends BaseDaoImpl<NebulaScript,Integer> implements INebulaScriptDao {
}
