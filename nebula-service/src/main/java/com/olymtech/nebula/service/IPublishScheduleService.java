/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.NebulaPublishSchedule;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;

import java.util.List;

/**
 * Created by Gavin on 2015-11-06 15:52.
 */
public interface IPublishScheduleService {

    public Integer logScheduleByAction(Integer eventId, PublishAction publishAction, PublishActionGroup publishActionGroup, Boolean isSuccessAction, String errorMsg);

    public List<NebulaPublishSchedule> selectByEventId(Integer eventId);

    public void deleteByEventIdWithOutCreateAction(Integer eventId);

    List<NebulaPublishSchedule> selectAllPaging(DataTablePage dataTablePage, NebulaPublishSchedule publishSchedule);

    NebulaPublishSchedule selectEntryAction(Integer eventId, PublishAction publishAction, PublishActionGroup actionGroup);

    void deleteById(Integer id);

    void update(NebulaPublishSchedule publishSchedule);
}
