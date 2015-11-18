package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.dao.IAclRoleDao;
import com.olymtech.nebula.entity.AclRole;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.service.IAclRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by WYQ on 2015/11/18.
 */
@Service("aclRoleServiceImpl")
public class AclRoleServiceImpl implements IAclRoleService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IAclRoleDao aclRoleDao;

    @Override
    public int insertAclRole(AclRole aclRole) {
        return aclRoleDao.insert(aclRole);
    }

    @Override
    public void deleteAclRoleById(Integer id) {
        aclRoleDao.deleteById(id);
    }

    @Override
    public void updateAclRole(AclRole aclRole) {
        aclRoleDao.update(aclRole);
    }

    @Override
    public PageInfo getPageInfoAclRole(DataTablePage dataTablePage) {
        PageHelper.startPage(dataTablePage.getPageNum(), dataTablePage.getPageSize());
        List<AclRole> roles = aclRoleDao.selectAllPaging(new AclRole());
        PageInfo pageInfo = new PageInfo(roles);
        return pageInfo;
    }
}
