package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.dao.IAclRoleDao;
import com.olymtech.nebula.dao.IAclRolePermissionDao;
import com.olymtech.nebula.entity.AclRole;
import com.olymtech.nebula.entity.AclRolePermission;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.service.IAclRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.ResponseWrapper;
import java.util.List;

/**
 * Created by WYQ on 2015/11/18.
 */
@Service
public class AclRoleServiceImpl implements IAclRoleService{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IAclRoleDao aclRoleDao;

    @Resource
    private IAclRolePermissionDao aclRolePermissionDao;

    @Override
    public int insertAclRole(AclRole aclRole, List<Integer> permissionIds) {
        Integer id = aclRoleDao.insert(aclRole);
        for (Integer permissionId : permissionIds) {
            AclRolePermission aclRolePermission = new AclRolePermission();
            aclRolePermission.setRoleId(id);
            aclRolePermission.setPermissionId(permissionId);
            aclRolePermissionDao.insert(aclRolePermission);
        }
        return id;
    }

    @Override
    public void deleteAclRoleById(Integer id) {
        aclRoleDao.deleteById(id);
    }

    @Override
    public void updateAclRole(AclRole aclRole,List<Integer> permissionIds) {
        for(Integer permissionId : permissionIds) {
            for (int i = 0; i < permissionIds.size(); i++) {
//                if (permissionId==)
            }
        }
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
