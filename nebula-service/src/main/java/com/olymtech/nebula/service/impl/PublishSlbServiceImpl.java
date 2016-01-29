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
    private INebulaPublishSlbDao iNebulaPublishSlbDao;

    @Override
    public Integer insertNebulaPublishSlb(NebulaPublishSlb nebulaPublishSlb) {
        Integer id = iNebulaPublishSlbDao.insert(nebulaPublishSlb);
        return id;
    }

    @Override
    public void deleteById(Integer id) {
        if (id != null) {
            iNebulaPublishSlbDao.deleteById(id);
        }
    }

    @Override
    public void updateByIdSeletive(NebulaPublishSlb nebulaPublishSlb) {
        if (nebulaPublishSlb.getId()!=null) {
            iNebulaPublishSlbDao.updateByIdSelective(nebulaPublishSlb);
        }
    }

    @Override
    public NebulaPublishSlb selectById(Integer id) {
        NebulaPublishSlb nebulaPublishSlb = iNebulaPublishSlbDao.selectById(id);
        return nebulaPublishSlb;
    }

    @Override
    public List<NebulaPublishSlb> selectAllWithParameter(NebulaPublishSlb nebulaPublishSlb) {
        List<NebulaPublishSlb> nebulaPublishSlbList = iNebulaPublishSlbDao.selectAllPaging(nebulaPublishSlb);
        return nebulaPublishSlbList;
    }

}
