/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageHelper;
import com.olymtech.nebula.core.action.Action;
import com.olymtech.nebula.dao.INebulaPublishScheduleDao;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.NebulaPublishEvent;
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
    public Integer logScheduleByAction(Integer eventId, PublishAction publishAction, PublishActionGroup publishActionGroup, Boolean isSuccessAction, String errorMsg) {
        NebulaPublishSchedule scheduleSearch = new NebulaPublishSchedule(eventId, publishAction, publishActionGroup);
        NebulaPublishSchedule nebulaPublishSchedule = new NebulaPublishSchedule(eventId, publishAction, publishActionGroup, isSuccessAction, errorMsg);
        List<NebulaPublishSchedule> schedulesInDB = nebulaPublishScheduleDao.selectAllPaging(scheduleSearch);
        Integer reusltId = null;
        if (schedulesInDB == null) {
            return reusltId;
        }
        if (schedulesInDB.size() == 0) {
            reusltId = nebulaPublishScheduleDao.insert(nebulaPublishSchedule);
        }
        if (schedulesInDB.size() == 1) {
            NebulaPublishSchedule scheduleInDB = schedulesInDB.get(0);
            nebulaPublishSchedule.setId(scheduleInDB.getId());
            nebulaPublishScheduleDao.update(nebulaPublishSchedule);
            reusltId = scheduleInDB.getId();
        }
        return reusltId;
    }

    @Override
    public List<NebulaPublishSchedule> selectByEventId(Integer eventId) {
        return nebulaPublishScheduleDao.selectByEventId(eventId);
    }

    @Override
    public void deleteByEventIdWithOutCreateAction(Integer eventId) {
        List<NebulaPublishSchedule> nebulaPublishSchedules = nebulaPublishScheduleDao.selectByEventId(eventId);
        for (NebulaPublishSchedule nebulaPublishSchedule : nebulaPublishSchedules) {
            if (nebulaPublishSchedule.getPublishAction() != PublishAction.CREATE_PUBLISH_EVENT) {
                nebulaPublishScheduleDao.deleteById(nebulaPublishSchedule.getId());
            }
        }
    }

    @Override
    public List<NebulaPublishSchedule> selectAllPaging(DataTablePage dataTablePage, NebulaPublishSchedule publishSchedule){
        PageHelper.startPage(dataTablePage.getPageNum(), dataTablePage.getPageSize());
        return nebulaPublishScheduleDao.selectAllPaging(publishSchedule);
    }

    /**
     * 找到一个action记录
     * 查询出多个为异常，返回null
     * 查询出一个，为正常
     * @param eventId
     * @param publishAction
     * @param actionGroup
     * @return
     */
    @Override
    public NebulaPublishSchedule selectEntryAction(Integer eventId,PublishAction publishAction,PublishActionGroup actionGroup){
        NebulaPublishSchedule publishSchedule = new NebulaPublishSchedule(eventId,publishAction,actionGroup);
        DataTablePage dataTablePage = new DataTablePage(1,10);
        List<NebulaPublishSchedule> nebulaPublishSchedules = selectAllPaging(dataTablePage,publishSchedule);
        if(nebulaPublishSchedules.size() == 1){
            return nebulaPublishSchedules.get(0);
        }else{
            return null;
        }
    }

    @Override
    public void deleteById(Integer id){
        nebulaPublishScheduleDao.deleteById(id);
    }

    @Override
    public void update(NebulaPublishSchedule publishSchedule){
        nebulaPublishScheduleDao.update(publishSchedule);
    }

}
