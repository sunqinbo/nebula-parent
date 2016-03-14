package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.enums.LogAction;

/**
 * Created by wangyiqiang on 16/3/11.
 */
public interface IPublishEventLogService  {

    /*日志记录发布动作*/
    Integer logPublishAction(Integer eventId, LogAction logAction, String logInfo, Integer optEmpId);
}
