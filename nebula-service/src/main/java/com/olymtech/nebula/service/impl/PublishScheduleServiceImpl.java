/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishScheduleDao;
import com.olymtech.nebula.entity.NebulaPublishSchedule;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.service.IPublishScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gavin on 2015-11-06 15:51.
 */
@Service("publishScheduleService")
public class PublishScheduleServiceImpl implements IPublishScheduleService {

    @Resource
    INebulaPublishScheduleDao nebulaPublishScheduleDao;

    @Override
    public Integer logScheduleByAction(Integer eventId, PublishAction publishAction, Boolean isSuccessAction,String errorMsg){
        NebulaPublishSchedule nebulaPublishSchedule = new NebulaPublishSchedule();
        nebulaPublishSchedule.setPublishEventId(eventId);
        nebulaPublishSchedule.setPublishAction(publishAction);
        nebulaPublishSchedule.setIsSuccessAction(isSuccessAction);
        nebulaPublishSchedule.setErrorMsg(errorMsg);
        return nebulaPublishScheduleDao.insert(nebulaPublishSchedule);
    }

    @Override
    public List<NebulaPublishSchedule> selectByEventId(Integer eventId){
        return nebulaPublishScheduleDao.selectByEventId(eventId);
    }

}
