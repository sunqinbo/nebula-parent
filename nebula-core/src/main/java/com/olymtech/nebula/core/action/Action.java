/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action;

import com.olymtech.nebula.entity.NebulaPublishEvent;

/**
 * Created by taoshanchang on 15/11/4.
 */
public interface Action {

    /**
     * 发布动作
     * @param event
     * @return
     * @throws Exception
     */
    public boolean doAction(NebulaPublishEvent event) throws Exception;

    /**
     * 发布动作失败后执行动作
     * @param event
     */
    public void doFailure(NebulaPublishEvent event);


}
