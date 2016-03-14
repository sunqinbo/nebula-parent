package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishEventLogDao;
import com.olymtech.nebula.entity.NebulaPublishEventLog;
import com.olymtech.nebula.entity.enums.LogAction;
import com.olymtech.nebula.service.IPublishEventLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wangyiqiang on 16/3/11.
 */
@Service
public class PublishEventLogServiceImpl implements IPublishEventLogService {
    @Resource
    private INebulaPublishEventLogDao nebulaPublishEventLogDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /*日志记录发布动作*/
    @Override
    public Integer logPublishAction(Integer eventId, LogAction logAction, String logInfo, Integer optEmpId) {
        Integer resultId = null;
        try {
            NebulaPublishEventLog nebulaPublishEventLog = new NebulaPublishEventLog(eventId,logAction,logInfo,optEmpId);
            List<NebulaPublishEventLog> publishEventLogsInDB = nebulaPublishEventLogDao.selectAllPaging(nebulaPublishEventLog);
            resultId = null;
            if (publishEventLogsInDB == null) {
                return resultId;
            }
            if (publishEventLogsInDB.size() == 0) {
                nebulaPublishEventLog.setLogDatetime(new Date());
                resultId = nebulaPublishEventLogDao.insert(nebulaPublishEventLog);
            }
            if (publishEventLogsInDB.size() == 1) {
                NebulaPublishEventLog publishEventLogInDB = publishEventLogsInDB.get(0);
                nebulaPublishEventLog.setLogDatetime(new Date());
                nebulaPublishEventLog.setId(publishEventLogInDB.getId());
                nebulaPublishEventLogDao.update(nebulaPublishEventLog);
                resultId = publishEventLogInDB.getId();
            }
        } catch (Exception e) {
            logger.error("日志记录异常:",e);
        }
        return resultId;
    }
}
