package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.BaseDO;
import com.olymtech.nebula.entity.NebulaPublishEvent;

/**
 * Created by liwenji on 2015/11/4.
 */
public interface IPublishEventService {

    public int createPublishEvent(NebulaPublishEvent nebulaPublishEvent);

    public BaseDO getPublishEventById(Integer id);
}
