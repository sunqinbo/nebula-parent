package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.common.utils.DateUtils;
import com.olymtech.nebula.dao.INebulaPublishEventDao;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.entity.enums.PublishStatus;
import com.olymtech.nebula.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.olymtech.nebula.common.utils.DateUtils.getKeyDate;

/**
 * Created by liwenji on 2015/11/4.
 */
@Service
public class PublishEventServiceImpl implements IPublishEventService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    INebulaPublishEventDao nebulaPublishEventDao;

    @Resource
    INebulaPublishModuleService nebulaPublishModuleService;

    @Resource
    IPublishAppService publishAppService;

    @Resource
    IPublishHostService publishHostService;

    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Autowired
    private IPublishBaseService publishBaseService;

    @Resource
    private IElkLogService elkLogService;


    @Override
    public void update(NebulaPublishEvent nebulaPublishEvent) {
        nebulaPublishEventDao.update(nebulaPublishEvent);
    }

    @Override
    public int createPublishEvent(NebulaPublishEvent nebulaPublishEvent) {
        Date now = new Date();
        String dateKey = getKeyDate(now);
        String key = nebulaPublishEvent.getPublishEnv() + "." + nebulaPublishEvent.getPublishProductName() + "." + dateKey;
        nebulaPublishEvent.setPublishProductKey(key);
        nebulaPublishEvent.setSubmitDatetime(now);
        Integer id = nebulaPublishEventDao.insert(nebulaPublishEvent);
        publishScheduleService.logScheduleByAction(id, PublishAction.CREATE_PUBLISH_EVENT, PublishActionGroup.PRE_MASTER, true, "");
        return id;
    }

    @Override
    public NebulaPublishEvent getPublishEventById(Integer id) {
        return nebulaPublishEventDao.selectById(id);
    }

    @Override
    public PageInfo getPublishEvent(DataTablePage dataTablePage, NebulaPublishEvent nebulaPublishEvent) {
        PageHelper.startPage(dataTablePage.getPageNum(), dataTablePage.getPageSize());
        List<NebulaPublishEvent> nebulaPublishEvents = nebulaPublishEventDao.selectAllPagingWithUser(nebulaPublishEvent);
        PageInfo pageInfo = new PageInfo(nebulaPublishEvents);
        return pageInfo;
    }

    @Override
    public NebulaPublishEvent selectById(Integer id) {
        return nebulaPublishEventDao.selectById(id);
    }

    @Override
    public NebulaPublishEvent selectWithChildByEventId(Integer eventId) {
        NebulaPublishEvent nebulaPublishEvent = nebulaPublishEventDao.selectById(eventId);
        List<NebulaPublishModule> publishModules = nebulaPublishModuleService.selectByEventId(eventId);
        for (NebulaPublishModule publishModule : publishModules) {
            List<NebulaPublishApp> publishApps = publishAppService.selectByEventIdAndModuleId(eventId, publishModule.getId());
            List<NebulaPublishHost> publishHosts = publishHostService.selectByEventIdAndModuleId(eventId, publishModule.getId());
            publishModule.setPublishApps(publishApps);
            publishModule.setPublishHosts(publishHosts);
        }
        nebulaPublishEvent.setPublishModules(publishModules);
        return nebulaPublishEvent;
    }

    /**
     * 重新发布
     *
     * @param eventId
     * @return
     */
    @Override
    public Boolean retryPublishRollback(Integer eventId) {
        Boolean result = false;
        try {
            publishHostService.deleteByEventId(eventId);
            publishAppService.deleteByEventId(eventId);
            nebulaPublishModuleService.deleteByEventId(eventId);
            publishScheduleService.deleteByEventIdWithOutCreateAction(eventId);
            result = true;
        } catch (Exception e) {
            logger.error("retryPublishRollback error:", e);
        }
        return result;
    }

    @Override
    public List<NebulaPublishEvent> selectAllPagingWithUser(NebulaPublishEvent event) {
        return nebulaPublishEventDao.selectAllPagingWithUser(event);
    }

    @Override
    public Integer selectCountWithUser(NebulaPublishEvent event) {
        return nebulaPublishEventDao.selectCountWithUser(event);
    }

    @Override
    public List<NebulaPublishEvent> isPUBLISHING(NebulaPublishEvent nebulaPublishEvent) {
        List<NebulaPublishEvent> nebulaPublishEvents = new ArrayList<>();
        nebulaPublishEvents = nebulaPublishEventDao.selectNoPUBLISHING(nebulaPublishEvent);
        return nebulaPublishEvents;
    }

    @Override
    public int getLastPublishId(Integer eventId) {
        NebulaPublishEvent nebulaPublishEvent = new NebulaPublishEvent();
        nebulaPublishEvent.setPid(eventId);
        if (nebulaPublishEventDao.selectAllPaging(nebulaPublishEvent).size() > 0) {
            return nebulaPublishEventDao.selectAllPaging(nebulaPublishEvent).get(0).getId();
        } else
            return -1;
    }

    @Override
    public void updateByIdSelective(NebulaPublishEvent nebulaPublishEventReal) {
        nebulaPublishEventDao.updateByIdSelective(nebulaPublishEventReal);
    }

    /**
     * 更新错误数,发布状态
     */
    @Override
    public Boolean updateLogCountSum(Boolean isSuccessPublish, PublishStatus publishStatus, NebulaPublishEvent publishEvent) {
        /*获取日志错误数,错误数等于所有机器总和*/
        Integer errorCountSum = 0;
        Integer exceptionCountSum = 0;
        Map<String, Integer> map = new HashMap<>();
        List<NebulaPublishHost> nebulaPublishHosts = publishHostService.selectByEventIdAndModuleId(publishEvent.getId(), null);
        if (PublishStatus.PUBLISHED == publishEvent.getPublishStatus() || PublishStatus.ROLLBACK == publishEvent.getPublishStatus() || PublishStatus.CANCEL == publishEvent.getPublishStatus()) {
        } else {
            for (NebulaPublishHost nebulaPublishHost : nebulaPublishHosts) {
                Integer errorCount = getPublishLogHostLogCount(publishEvent, nebulaPublishHost, "ERROR");
                Integer exceptionCount = getPublishLogHostLogCount(publishEvent, nebulaPublishHost, "EXCEPTION");
                errorCountSum += errorCount;
                exceptionCountSum += exceptionCount;
            }
        }
        publishEvent.setIsSuccessPublish(isSuccessPublish);
        publishEvent.setPublishStatus(publishStatus);
        publishEvent.setPublishEndDatetime(new Date());
        publishEvent.setCountError(errorCountSum);
        publishEvent.setCountException(exceptionCountSum);
        nebulaPublishEventDao.update(publishEvent);
        return true;

    }

    /**
     * 获取log count
     */
    @Override
    public int getPublishLogHostLogCount(NebulaPublishEvent publishEvent, NebulaPublishHost publishHost, String logType) {
//        NebulaPublishEvent publishEvent = (NebulaPublishEvent) publishEventService.getPublishEventById(eventId);

        if (publishEvent.getPublishDatetime() == null) {
            return 0;
        }
        Date fromDate = DateUtils.getDateByGivenHour(publishEvent.getPublishDatetime(), -8);
        Date toDate = DateUtils.getDateByGivenHour(new Date(), -8);
        ElkSearchData elkSearchData = new ElkSearchData(publishHost.getPassPublishHostName(), logType, fromDate, toDate, 1, 10);
        return elkLogService.count(elkSearchData);
    }


}
