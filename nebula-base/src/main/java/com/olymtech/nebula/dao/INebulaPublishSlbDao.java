package com.olymtech.nebula.dao;

import com.olymtech.nebula.entity.NebulaPublishSlb;

import java.util.List;

public interface INebulaPublishSlbDao extends IBaseDao<NebulaPublishSlb,Integer>{


    List<NebulaPublishSlb> selectByPublishEventId(Integer eventId);
}