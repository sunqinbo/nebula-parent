/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.enums.PublishAction;

/**
 * Created by Gavin on 2015-11-06 15:52.
 */
public interface IPublishScheduleService {

    public Integer logScheduleByAction(Integer eventId, PublishAction publishAction, Boolean isSuccessAction, String errorMsg);

}
