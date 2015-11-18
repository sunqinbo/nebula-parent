package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.dao.INebulaPublishEventDao;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.olymtech.nebula.common.utils.DateUtils.getKeyDate;
import static com.olymtech.nebula.common.utils.DateUtils.longDate;

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
    public PageInfo getPublishEvent(DataTablePage dataTablePage) {
        PageHelper.startPage(dataTablePage.getPageNum(), dataTablePage.getPageSize());
        List<NebulaPublishEvent> nebulaPublishEvents = nebulaPublishEventDao.selectAllPaging(new NebulaPublishEvent());
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

}
