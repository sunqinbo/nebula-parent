/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageHelper;
import com.olymtech.nebula.dao.INebulaPublishBaseDao;
import com.olymtech.nebula.entity.NebulaPublishBase;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishModule;
import com.olymtech.nebula.service.IPublishBaseService;
import com.trilead.ssh2.packets.PacketUserauthBanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gavin on 2015-11-05 15:50.
 */
@Service("publishBaseService")
public class PublishBaseServiceImpl implements IPublishBaseService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private INebulaPublishBaseDao   nebulaPublishBaseDao;

    @Override
    public String selectLastModuleKeyByPublishEvent(NebulaPublishEvent event,String moduleName){
        String publishKey = null;
        try{
            NebulaPublishBase baseSearch = new NebulaPublishBase();
            baseSearch.setPublishModuleName(moduleName);
            baseSearch.setPublishProductName(event.getPublishProductName());
            baseSearch.setPublishProductEnv(event.getPublishEnv());
            PageHelper.startPage(1, 10);
            List<NebulaPublishBase> nebulaPublishBases = nebulaPublishBaseDao.selectAllPaging(baseSearch);
            if(nebulaPublishBases != null && nebulaPublishBases.size()>0){
                NebulaPublishBase nebulaPublishBase = nebulaPublishBases.get(0);
                if(nebulaPublishBase != null){
                    publishKey = nebulaPublishBase.getPublishModuleKey();
                }
            }
        }catch (Exception e){
            logger.error("selectLastModuleKeyByPublishEvent error:",e);
        }
        return publishKey;
    }

    @Override
    public String checkoutHistoryDirKey(NebulaPublishEvent event, NebulaPublishModule module){
        String historyDirKey = null;
        NebulaPublishBase publishBase = new NebulaPublishBase(event.getPublishProductName(),module.getPublishModuleName(),event.getPublishEnv());
        List<NebulaPublishBase> nebulaPublishBases = nebulaPublishBaseDao.selectAllPaging(publishBase);
        /** 如果空，则返回空*/
        if(nebulaPublishBases == null){
            return historyDirKey;
        }
        if(nebulaPublishBases.size() >=6){
            historyDirKey = nebulaPublishBases.get(5).getPublishModuleKey();
        }
        return historyDirKey;
    }

}
