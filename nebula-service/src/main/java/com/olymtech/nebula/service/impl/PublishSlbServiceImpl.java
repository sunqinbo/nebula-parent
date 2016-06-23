package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.INebulaPublishSlbDao;
import com.olymtech.nebula.entity.NebulaPublishSlb;
import com.olymtech.nebula.service.IPublishSlbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangyiqiang on 16/1/29.
 */
@Service("publishSlbService")
public class PublishSlbServiceImpl implements IPublishSlbService {

    @Resource
    private INebulaPublishSlbDao nebulaPublishSlbDao;

    @Override
    public Integer insertNebulaPublishSlb(NebulaPublishSlb nebulaPublishSlb) {
        Integer id = nebulaPublishSlbDao.insert(nebulaPublishSlb);
        return id;
    }

    @Override
    public void deleteById(Integer id) {
        if (id != null) {
            nebulaPublishSlbDao.deleteById(id);
        }
    }

    @Override
    public void updateByIdSeletive(NebulaPublishSlb nebulaPublishSlb) {
        if (nebulaPublishSlb.getId()!=null) {
            nebulaPublishSlbDao.updateByIdSelective(nebulaPublishSlb);
        }
    }

    @Override
    public NebulaPublishSlb selectById(Integer id) {
        NebulaPublishSlb nebulaPublishSlb = nebulaPublishSlbDao.selectById(id);
        return nebulaPublishSlb;
    }

    @Override
    public List<NebulaPublishSlb> selectAllWithParameter(NebulaPublishSlb nebulaPublishSlb) {
        List<NebulaPublishSlb> nebulaPublishSlbList = nebulaPublishSlbDao.selectAllPaging(nebulaPublishSlb);
        return nebulaPublishSlbList;
    }

    @Override
    public List<NebulaPublishSlb> selectByPublishEventId(Integer eventId){
        return nebulaPublishSlbDao.selectByPublishEventId(eventId);
    }

    @Override
    public List<NebulaPublishSlb> selectByPublishEventIdAndModuleId(Integer eventId, Integer moduleId) {
        NebulaPublishSlb publishSlbSearch = new NebulaPublishSlb(eventId,moduleId);
        return nebulaPublishSlbDao.selectAllPaging(publishSlbSearch);
    }

}
