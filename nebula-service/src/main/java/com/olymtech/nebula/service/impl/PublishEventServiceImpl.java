package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishEventDao;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.service.IPublishEventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.olymtech.nebula.common.utils.DateUtils.getKeyDate;

/**
 * Created by liwenji on 2015/11/4.
 */
@Service
public class PublishEventServiceImpl implements IPublishEventService {
    @Resource
    INebulaPublishEventDao nebulaPublishEventDao;

    @Override
    public int createPublishEvent(NebulaPublishEvent nebulaPublishEvent) {
        Date now = new Date();
        String date=getKeyDate(now);
        String key=nebulaPublishEvent.getPublishEnv()+"."+nebulaPublishEvent.getPublishProductName()+"."+date;
        nebulaPublishEvent.setPublishProductKey(key);
        nebulaPublishEvent.setSubmitDatetime(now);
        return nebulaPublishEventDao.insert(nebulaPublishEvent);
    }
}
