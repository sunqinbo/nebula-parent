package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishSequenceDao;
import com.olymtech.nebula.dao.impl.NebulaPublishSequenceDaoImpl;
import com.olymtech.nebula.entity.NebulaPublishSequence;
import com.olymtech.nebula.service.IPublishSequenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liwenji on 2015/11/9.
 */
@Service("PublishSequenceService")
public class PublishSequenceServiceImpl implements IPublishSequenceService{

    @Resource
    INebulaPublishSequenceDao nebulaPublishSequenceDao;

    @Override
    public NebulaPublishSequence selectByActionName(String actionname) {
        return nebulaPublishSequenceDao.selectByActionName(actionname);
    }
}
