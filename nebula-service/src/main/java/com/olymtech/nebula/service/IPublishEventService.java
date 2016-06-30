package com.olymtech.nebula.service;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.core.action.ActionChain;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.entity.enums.PublishStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by liwenji on 2015/11/4.
 */
public interface IPublishEventService {

    public void update(NebulaPublishEvent nebulaPublishEvent);

    public int createPublishEvent(NebulaPublishEvent nebulaPublishEvent);

    public PageInfo getPublishEvent(DataTablePage dataTablePage, NebulaPublishEvent nebulaPublishEvent);

    public NebulaPublishEvent selectById(Integer eventId);

    public NebulaPublishEvent selectWithChildByEventId(Integer eventId);

    public Boolean retryPublishRollback(Integer eventId);

    public List<NebulaPublishEvent> selectAllPagingWithUser(NebulaPublishEvent event);

    public Integer selectCountWithUser(NebulaPublishEvent event);

    public List<NebulaPublishEvent> isPUBLISHING(NebulaPublishEvent nebulaPublishEvent);

    public int getLastPublishId(Integer eventId);

    public void updateByIdSelective(NebulaPublishEvent nebulaPublishEventReal);

    public Boolean updateLogCountSum(Boolean isSuccessPublish, PublishStatus publishStatus, NebulaPublishEvent publishEvent);

    public int getPublishLogHostLogCount(NebulaPublishEvent publishEvent, NebulaPublishHost publishHost, String logType);

    Boolean updateChangeList(NebulaPublishEvent publishEvent);

    List<FileChangeData> changeListJsonStringToList(String responseContext);

    Callback batchPublish(ActionChain chain, NebulaPublishEvent event, HttpServletRequest request, HttpServletResponse response);

    NebulaPublishEvent initNowBatchTag(NebulaPublishEvent event);
}
