package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishAppDao;
import com.olymtech.nebula.dao.INebulaPublishEventDao;
import com.olymtech.nebula.entity.NebulaPublishApp;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishHost;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.olymtech.nebula.common.utils.DateUtils.getKeyDate;

/**
 * Created by liwenji on 2015/11/4.
 */
@Service
public class PublishEventServiceImpl implements IPublishEventService {
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


    @Override
    public int createPublishEvent(NebulaPublishEvent nebulaPublishEvent) {
        Date now = new Date();
        String dateKey=getKeyDate(now);
        String key=nebulaPublishEvent.getPublishEnv()+"."+nebulaPublishEvent.getPublishProductName()+"."+dateKey;
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
    public List<NebulaPublishEvent> getPublishEvent() {
        return nebulaPublishEventDao.selectAllPaging(new NebulaPublishEvent());
    }

    @Override
    public NebulaPublishEvent selectById(Integer id){
        return  nebulaPublishEventDao.selectById(id);
    }

    @Override
    public NebulaPublishEvent selectWithChildByEventId(Integer eventId){
        NebulaPublishEvent nebulaPublishEvent = nebulaPublishEventDao.selectById(eventId);
        List<NebulaPublishModule> publishModules = nebulaPublishModuleService.selectByEventId(eventId);
        for(NebulaPublishModule publishModule:publishModules){
            List<NebulaPublishApp> publishApps = publishAppService.selectByEventIdAndModuleId(eventId, publishModule.getId());
            List<NebulaPublishHost> publishHosts = publishHostService.selectByEventIdAndModuleId(eventId,publishModule.getId());
            publishModule.setPublishApps(publishApps);
            publishModule.setPublishHosts(publishHosts);
        }
        nebulaPublishEvent.setPublishModules(publishModules);
        return nebulaPublishEvent;
    }

}
