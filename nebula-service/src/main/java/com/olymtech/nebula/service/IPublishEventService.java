package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.BaseDO;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishModule;

import java.util.List;

/**
 * Created by liwenji on 2015/11/4.
 */
public interface IPublishEventService {

    public void update(NebulaPublishEvent nebulaPublishEvent);

    public int createPublishEvent(NebulaPublishEvent nebulaPublishEvent);

    public NebulaPublishEvent getPublishEventById(Integer id);

    public PageInfo getPublishEvent(DataTablePage dataTablePage, NebulaPublishEvent nebulaPublishEvent);

    public NebulaPublishEvent selectById(Integer eventId);

    public NebulaPublishEvent selectWithChildByEventId(Integer eventId);

    public Boolean retryPublishRollback(Integer eventId);

    public List<NebulaPublishEvent> selectAllPagingWithUser(NebulaPublishEvent event);

    public Integer selectCountWithUser(NebulaPublishEvent event);

    public List<NebulaPublishEvent> isPUBLISHING(NebulaPublishEvent nebulaPublishEvent);

    public int getLastPublishId(Integer eventId);

    public void updateByIdSelective(NebulaPublishEvent nebulaPublishEventReal);
}
