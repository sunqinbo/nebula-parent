package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishEventDao;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.service.IPublishEventService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liwenji on 2015/11/4.
 */
@Service
public class PublishEventServiceImpl implements IPublishEventService {
    @Resource
    INebulaPublishEventDao nebulaPublishEventDao;

    @Override
    public int createPublishEvent(NebulaPublishEvent nebulaPublishEvent) {
        return nebulaPublishEventDao.insert(nebulaPublishEvent);
    }
}
