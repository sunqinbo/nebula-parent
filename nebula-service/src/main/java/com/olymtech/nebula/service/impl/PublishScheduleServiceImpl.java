/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishScheduleDao;
import com.olymtech.nebula.entity.NebulaPublishSchedule;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.IPublishScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gavin on 2015-11-06 15:51.
 */
@Service
public class PublishScheduleServiceImpl implements IPublishScheduleService {

    @Resource
    INebulaPublishScheduleDao nebulaPublishScheduleDao;

    @Override
    public Integer logScheduleByAction(Integer eventId, PublishAction publishAction,PublishActionGroup publishActionGroup, Boolean isSuccessAction,String errorMsg){
        NebulaPublishSchedule scheduleSearch = new NebulaPublishSchedule(eventId,publishAction,publishActionGroup);
        NebulaPublishSchedule nebulaPublishSchedule = new NebulaPublishSchedule(eventId,publishAction,publishActionGroup,isSuccessAction,errorMsg);
        List<NebulaPublishSchedule> schedulesInDB = nebulaPublishScheduleDao.selectAllPaging(scheduleSearch);
        Integer reusltId = null;
        if(schedulesInDB == null){
            return reusltId;
        }
        if(schedulesInDB.size() == 0){
            reusltId = nebulaPublishScheduleDao.insert(nebulaPublishSchedule);
        }
        if(schedulesInDB.size() == 1){
            NebulaPublishSchedule scheduleInDB = schedulesInDB.get(0);
            nebulaPublishSchedule.setId(scheduleInDB.getId());
            nebulaPublishScheduleDao.update(nebulaPublishSchedule);
            reusltId = scheduleInDB.getId();
        }
        return reusltId;
    }

    @Override
    public List<NebulaPublishSchedule> selectByEventId(Integer eventId){
        return nebulaPublishScheduleDao.selectByEventId(eventId);
    }

}
