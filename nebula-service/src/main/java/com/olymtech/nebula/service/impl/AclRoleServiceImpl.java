package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.dao.IAclPermissionDao;
import com.olymtech.nebula.dao.IAclRoleDao;
import com.olymtech.nebula.dao.IAclRolePermissionDao;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.service.IAclRoleService;
import com.olymtech.nebula.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYQ on 2015/11/18.
 */
@Service
public class AclRoleServiceImpl implements IAclRoleService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IAclRoleDao aclRoleDao;

    @Resource
    private IAclRolePermissionDao aclRolePermissionDao;

    @Resource
    private IAclPermissionDao aclPermissionDao;

    @Resource
    private IUserService userService;


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
        aclRolePermissionDao.deleteById(id);
    }

    @Override
    public void updateAclRole(AclRole aclRole, List<Integer> permissionIds) {
        aclRoleDao.update(aclRole);
        aclRolePermissionDao.deleteByRoleId(aclRole.getId());
        for (Integer permissionId : permissionIds) {
            AclRolePermission aclRolePermission = new AclRolePermission();
            aclRolePermission.setRoleId(aclRole.getId());
            aclRolePermission.setPermissionId(permissionId);
            aclRolePermissionDao.insert(aclRolePermission);
        }
    }

    @Override
    public PageInfo getPageInfoAclRole(DataTablePage dataTablePage) {
        PageHelper.startPage(dataTablePage.getPageNum(), dataTablePage.getPageSize());
        List<AclRole> roles = aclRoleDao.selectAllPaging(new AclRole());
        PageInfo pageInfo = new PageInfo(roles);
        return pageInfo;
    }

    @Override
    public AclRole getAclRoleWithPermissionsByRoleId(Integer roleId) {
        AclRole aclRole = aclRoleDao.selectById(roleId);
        List<Integer> permissionIds = new ArrayList<>();
        List<AclPermission> permissions = new ArrayList<>();
        List<AclRolePermission> aclRolePermissions = aclRolePermissionDao.selectByRoleId(roleId);
        for (AclRolePermission aclRolePermission : aclRolePermissions) {
            permissionIds.add(aclRolePermission.getPermissionId());
        }
        for (Integer permissionId : permissionIds) {
            permissions.add(aclPermissionDao.selectById(permissionId));
        }
        aclRole.setAclPermissions(permissions);
        return aclRole;
    }

    @Override
    public List<Select2Data> getAllRoles() {
        List<Select2Data> select2Datas = new ArrayList<>();
        List<AclRole> aclRoles = aclRoleDao.selectAllPaging(new AclRole());
        for (AclRole aclRole : aclRoles) {
            Select2Data select2Data = new Select2Data();
            select2Data.setId(aclRole.getId());
            select2Data.setText(aclRole.getRoleCname());
            select2Datas.add(select2Data);
        }
        return select2Datas;
    }

    @Override
    public List<Select2Data> getSelect2Datas(List<AclRole> aclRoles,NebulaUserInfo loginUser) {
        List<AclRole> aclRoleList = aclRoleDao.selectAllPaging(new AclRole());
        List<Select2Data> select2Datas = new ArrayList<>();
        for (AclRole aclRole : aclRoleList) {
            Select2Data select2Data = new Select2Data();
            select2Data.setId(aclRole.getId());
            select2Data.setText(aclRole.getRoleCname());
            if (aclRoles != null) {
                for (AclRole aclRoleHas : aclRoles) {
                    if (aclRole.getId() == aclRoleHas.getId()) {
                        select2Data.setSelected(true);
                    }
                }
            }

            if(userService.userRoleIsNeedRole(loginUser,"root")){
                select2Datas.add(select2Data);
            }else if(userService.userRoleIsNeedRole(loginUser,"admin")){
                if(!aclRole.getRoleName().equals("root")){
                    select2Datas.add(select2Data);
                }
            }else{
                if(!aclRole.getRoleName().equals("root") || !aclRole.getRoleName().equals("admin")){
                    select2Datas.add(select2Data);
                }
            }
        }
        return select2Datas;
    }


}
