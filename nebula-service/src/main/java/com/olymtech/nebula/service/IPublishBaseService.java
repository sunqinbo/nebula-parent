/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NebulaPublishBase;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishModule;

/**
 * Created by Gavin on 2015-11-05 15:49.
 */
public interface IPublishBaseService {

    public Integer insertAndUpdate(NebulaPublishBase publishBase);

    /**
     * 获取上一个版本key
     * 用于回滚
     * @param event
     * @param moduleName
     * @return
     */
    public String selectLastModuleKeyByPublishEvent(NebulaPublishEvent event, String moduleName);

    /**
     * 保留五个版本，返回五个版本前，旧的版本key
     * 用于清理历史目录，可能为空
     * @param event
     * @param module
     * @return
     */
    public String checkoutHistoryDirKey(NebulaPublishEvent event, NebulaPublishModule module);

}