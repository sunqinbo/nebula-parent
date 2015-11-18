package com.olymtech.nebula.service.impl;

import com.olymtech.nebula.dao.IAclUserRoleDao;
import com.olymtech.nebula.entity.AclPermission;
import com.olymtech.nebula.entity.AclUserRole;
import com.olymtech.nebula.service.IAclUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by WYQ on 2015/11/17.
 */
@Service("aclUserRoleService")
public class AclUserRoleServiceImp implements IAclUserRoleService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IAclUserRoleDao aclUserRoleDao;

    @Override
    public int insertAclUserRole(AclUserRole aclUserRole) {
        return aclUserRoleDao.insert(aclUserRole);
    }

    @Override
    public void deleteAclUserRoleById(Integer id) {
        aclUserRoleDao.deleteById(id);
    }

    @Override
    public void updateAclUserRole(AclUserRole aclUserRole) {
        aclUserRoleDao.update(aclUserRole);
    }


}
