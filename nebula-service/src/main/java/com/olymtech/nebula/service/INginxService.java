/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NginxServer;

import java.util.List;

/**
 * Created by Gavin on 2016-06-23 16:41.
 */
public interface INginxService {
    List<NginxServer> getMasterNginxServers();

}
