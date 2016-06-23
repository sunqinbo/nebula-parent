package com.olymtech.nebula.service;

import com.olymtech.nebula.entity.NebulaPublishSlb;

import java.util.List;

/**
 * Created by wangyiqiang on 16/1/29.
 */
public interface IPublishSlbService {
    Integer insertNebulaPublishSlb(NebulaPublishSlb nebulaPublishSlb);

    void deleteById(Integer id);

    void updateByIdSeletive(NebulaPublishSlb nebulaPublishSlb);

    NebulaPublishSlb selectById(Integer id);

    List<NebulaPublishSlb> selectAllWithParameter(NebulaPublishSlb nebulaPublishSlb);

    List<NebulaPublishSlb> selectByPublishEventId(Integer eventId);

    List<NebulaPublishSlb> selectByPublishEventIdAndModuleId(Integer eventId, Integer moduleId);
}
