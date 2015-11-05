/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageHelper;
import com.olymtech.nebula.dao.INebulaPublishBaseDao;
import com.olymtech.nebula.entity.NebulaPublishBase;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.service.IPublishBaseService;
import com.trilead.ssh2.packets.PacketUserauthBanner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Gavin on 2015-11-05 15:50.
 */
@Service("publishBaseService")
public class PublishBaseServiceImpl implements IPublishBaseService {
    @Resource
    private INebulaPublishBaseDao   nebulaPublishBaseDao;

    @Override
    public String selectLastModuleKeyByPublishEvent(NebulaPublishEvent event,String moduleName){
        String publishKey = null;
        NebulaPublishBase baseSearch = new NebulaPublishBase();
        baseSearch.setPublishModuleName(moduleName);
        baseSearch.setPublishProductName(event.getPublishProductName());
        baseSearch.setPublishProductEnv(event.getPublishEnv());
        PageHelper.startPage(0, 10);
        List<NebulaPublishBase> nebulaPublishBases = nebulaPublishBaseDao.selectAllPaging(baseSearch);
        if(nebulaPublishBases != null){
            NebulaPublishBase nebulaPublishBase = nebulaPublishBases.get(0);
            if(nebulaPublishBase != null){
                publishKey = nebulaPublishBase.getPublishModuleKey();
            }
        }
        return publishKey;
    }

}
