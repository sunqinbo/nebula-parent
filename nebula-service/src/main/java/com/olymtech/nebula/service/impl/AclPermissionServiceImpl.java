package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.dao.IAclPermissionDao;
import com.olymtech.nebula.entity.AclPermission;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.service.IAclPermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by WYQ on 2015/11/17.
 */
@Service("permissionService")
public class AclPermissionServiceImpl implements IAclPermissionService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IAclPermissionDao aclPermissionDao;

    @Override
    public int insertAclPermission(AclPermission permission) {
        return aclPermissionDao.insert(permission);
    }

    @Override
    public void deleteAclPermissionById(Integer id) {
        aclPermissionDao.deleteById(id);
    }

    @Override
    public void updateAclPermission(AclPermission permission) {
        aclPermissionDao.update(permission);
    }

    @Override
    public PageInfo getPageInfoAclPermission(DataTablePage dataTablePage) {
        PageHelper.startPage(dataTablePage.getPageNum(), dataTablePage.getPageSize());
        List<AclPermission> permissions = aclPermissionDao.selectAllPaging(new AclPermission());
        PageInfo pageInfo = new PageInfo(permissions);
        return pageInfo;
    }
}
