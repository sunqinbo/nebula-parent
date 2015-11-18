package com.olymtech.nebula.controller;

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
            logger.error("insertAclUserRole error:", e);
        }
        return returnCallback("Error", "插入用户角色失败");
    }


    @RequestMapping(value = "userRole/deleteAclUserRole.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public Callback deleteAclUserRole(Integer id) {
        try {
            aclUserRoleService.deleteAclUserRoleById(id);
            return returnCallback("Success", "删除用户角色成功");
        } catch (Exception e) {
            logger.error("deleteAclUserRoleById error:", e);
        }
        return returnCallback("Error", "删除用户角色失败");
    }

    @RequestMapping(value = "userRole/updateAclUserRole.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public Callback updateAclUserRole(AclUserRole aclUserRole) {
        try {
            aclUserRoleService.updateAclUserRole(aclUserRole);
            return returnCallback("Success", "更新用户角色成功");
        }catch (Exception e) {
            logger.error("updateAclUserRole error:", e);
        }
        return returnCallback("Error","更新用户角色失败");
    }

}
