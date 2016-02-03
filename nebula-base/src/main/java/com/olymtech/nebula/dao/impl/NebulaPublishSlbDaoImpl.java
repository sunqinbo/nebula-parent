package com.olymtech.nebula.dao.impl;

import com.olymtech.nebula.dao.INebulaPublishSlbDao;
import com.olymtech.nebula.entity.NebulaPublishSlb;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("nebulaPublishSlbDao")
public class NebulaPublishSlbDaoImpl extends BaseDaoImpl<NebulaPublishSlb, Integer> implements INebulaPublishSlbDao {

    @Override
    public List<NebulaPublishSlb> selectByPublishEventId(Integer eventId){
        return getSqlSession().selectList(CLASS_NAME + "-Select-By-Publish-EventId", eventId);
    }
}