package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.BaseDO;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishModule;

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

    /**
     * 保留五个版本，返回五个版本前，旧的版本key
     * 用于清理历史目录，可能为空
     * @param event
     * @param module
     * @return
     */
    public String checkoutHistoryDirKey(NebulaPublishEvent event, NebulaPublishModule module);


    /**
     * 获取上一个版本key
     * 用于回滚
     * @param event
     * @param module
     * @return
     */
    public String checkoutLastDirKey(NebulaPublishEvent event, NebulaPublishModule module);
}
