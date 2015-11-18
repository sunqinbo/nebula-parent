package com.olymtech.nebula.controller;

import com.olymtech.nebula.entity.AclPermission;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.service.IPermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by WYQ on 2015/11/17.
 */
@Controller("permissionController")
@RequestMapping("/")
public class PermissionController extends BaseController {

    @Resource
    private IPermissionService permissionService;

    @RequestMapping(value = "permission/insertAclPermission.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public Callback insertAclPermission(AclPermission permission) {
        try {
            permissionService.insertAclPermission(permission);
            return returnCallback("Success", "插入权限成功");
        } catch (Exception e) {
            logger.error("insertAclPermission error:", e);
        }
        return returnCallback("Error", "插入权限失败");
    }

    @RequestMapping(value = "permission/deleteAclPermission.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public Callback deleteAclPermission(Integer id) {
        try {
            permissionService.deleteAclPermissionById(id);
            return returnCallback("Success", "删除权限成功");
        } catch (Exception e) {
            logger.error("deleteAclPermission error:", e);
        }
        return returnCallback("Error", "删除权限失败");
    }

    @RequestMapping(value = "permission/updateAclPermission.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public Callback updateAclPermission(AclPermission permission) {
        try {
            permissionService.updateAclPermission(permission);
            return returnCallback("Success", "更新权限成功");
        }catch (Exception e) {
            logger.error("updateAclPermission error:", e);
        }
        return returnCallback("Error","更新权限失败");
    }


}


