/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishSvnBaselineDao;
import com.olymtech.nebula.entity.NebulaPublishSvnBaseline;
import com.olymtech.nebula.service.IPublishSvnBaselineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Gavin on 2015-11-18 10:49.
 */
public class PublishSvnBaselineServiceImpl implements IPublishSvnBaselineService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private INebulaPublishSvnBaselineDao publishSvnBaselineDao;

    /**
     * 如果有记录则更新
     * @param publishSvnBaseline
     * @return
     */
    @Override
    public Integer insertAndUpdate(NebulaPublishSvnBaseline publishSvnBaseline){
        List<NebulaPublishSvnBaseline> publishSvnBaselines = publishSvnBaselineDao.selectAllPaging(publishSvnBaseline);
        if(publishSvnBaselines == null || publishSvnBaselines.size() == 0){
            return publishSvnBaselineDao.insert(publishSvnBaseline);
        }else{
            NebulaPublishSvnBaseline publishSvnBaselineInDB = publishSvnBaselines.get(0);
            publishSvnBaselineInDB.setProductName(publishSvnBaseline.getProductName());
            publishSvnBaselineInDB.setProductEnv(publishSvnBaseline.getProductEnv());
            publishSvnBaselineInDB.setProductSrcSvn(publishSvnBaseline.getProductSrcSvn());
            publishSvnBaselineInDB.setPublishEventId(publishSvnBaseline.getPublishEventId());
            publishSvnBaselineInDB.setSrcSvnVersion(publishSvnBaseline.getSrcSvnVersion());
            publishSvnBaselineDao.update(publishSvnBaselineInDB);
            return publishSvnBaselineInDB.getId();
        }
    }


}
