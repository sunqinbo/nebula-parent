package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.BaseDO;
import com.olymtech.nebula.entity.NebulaPublishEvent;

/**
 * Created by liwenji on 2015/11/4.
 */
public interface IPublishEventService {

    public int createPublishEvent(NebulaPublishEvent nebulaPublishEvent);

    public BaseDO getPublishEventById(Integer id);

    public PageInfo getPublishEvent(DataTablePage dataTablePage);

    public NebulaPublishEvent selectById(Integer eventId);

    public NebulaPublishEvent selectWithChildByEventId(Integer eventId);

    public Boolean retryPublishRollback(Integer eventId);

}
