package com.olymtech.nebula.controller;

import com.olymtech.nebula.entity.AclUserRole;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.service.IAclUserRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by WYQ on 2015/11/17.
 */
@Controller
@RequestMapping("/userRole")
public class AclUserRoleController extends BaseController {

    @Resource
    private IAclUserRoleService aclUserRoleService;

    @RequestMapping(value = "/insertAclUserRole.htm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback insertAclUserRole(AclUserRole aclUserRole) {
        aclUserRoleService.insertAclUserRole(aclUserRole);
        return returnCallback("Success", "插入用户角色成功");
    }


    @RequestMapping(value = "/deleteAclUserRole.htm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback deleteAclUserRole(Integer id) {
        aclUserRoleService.deleteAclUserRoleById(id);
        return returnCallback("Success", "删除用户角色成功");
    }

    @RequestMapping(value = "/updateAclUserRole.htm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback updateAclUserRole(AclUserRole aclUserRole) {
        aclUserRoleService.updateAclUserRole(aclUserRole);
        return returnCallback("Success", "更新用户角色成功");
    }

}
