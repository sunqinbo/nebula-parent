package com.olymtech.nebula.controller;

import com.olymtech.nebula.dao.IAclUserRoleDao;
import com.olymtech.nebula.entity.AclPermission;
import com.olymtech.nebula.entity.AclUserRole;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.service.IAclUserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by WYQ on 2015/11/17.
 */
@Controller("aclUserRoleController")
@RequestMapping("/")
public class AclUserRoleController extends BaseController{

    @Resource
    private IAclUserRoleService aclUserRoleService;

    @RequestMapping(value = "userRole/insertAclUserRole.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public Callback insertAclUserRole(AclUserRole aclUserRole) {
        try {
            aclUserRoleService.insertAclUserRole(aclUserRole);
            return returnCallback("Success","插入用户角色成功");
        } catch (Exception e) {
            logger.error("insertAclPermission error:", e);
        }
        return returnCallback("Error", "插入权限失败");
    }


//    @RequestMapping(value = "permission/deleteAclPermission.htm", method = {RequestMethod.POST, RequestMethod.GET})
//    public Callback deleteAclPermission(Integer id) {
//        try {
//            aclUserRoleDao.deleteAclPermissionById(id);
//            return returnCallback("Success", "删除权限成功");
//        } catch (Exception e) {
//            logger.error("deleteAclPermission error:", e);
//        }
//        return returnCallback("Error", "删除权限失败");
//    }
//
//    @RequestMapping(value = "permission/updateAclPermission.htm", method = {RequestMethod.POST, RequestMethod.GET})
//    public Callback updateAclPermission(AclPermission permission) {
//        try {
//            permissionService.updateAclPermission(permission);
//            return returnCallback("Success", "更新权限成功");
//        }catch (Exception e) {
//            logger.error("updateAclPermission error:", e);
//        }
//        return returnCallback("Error","更新权限失败");
//    }

}
