package com.olymtech.nebula.controller;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.AclRole;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.service.IAclRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * Created by WYQ on 2015/11/18.
 */
@Controller("aclRoleController")
@RequestMapping("/")
public class AclRoleController extends BaseController {

    @Resource
    private IAclRoleService aclRoleService;

    @RequestMapping(value = "role/insertAclRole.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public Callback insertAclRole(AclRole aclRole) {
        try {
            aclRoleService.insertAclRole(aclRole);
            return returnCallback("Success", "插入角色成功");
        } catch (Exception e) {
            logger.error("insertAclRole error", e);
        }
        return returnCallback("Error", "插入角色失败");
    }

    @RequestMapping(value = "role/deleteAclRole.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public Callback deleteAclRole(Integer id) {
        try {
            aclRoleService.deleteAclRoleById(id);
            return returnCallback("Success", "删除角色成功");
        } catch (Exception e) {
            logger.error("deleteAclRole error", e);
        }
        return returnCallback("Error", "删除角色失败");
    }

    @RequestMapping(value = "role/updateAclRole.htm", method = {RequestMethod.GET, RequestMethod.POST})
    public Callback updateAclRole(AclRole aclRole) {
        try {
            aclRoleService.updateAclRole(aclRole);
            return returnCallback("Success", "更新角色成功");
        } catch (Exception e) {
            logger.error("updateAclRole error", e);
        }
        return returnCallback("Error", "更新角色失败");
    }

    @RequestMapping(value = "role/selectAllPagingRole", method = {RequestMethod.POST, RequestMethod.GET})
    public Object selectAllPagingRole(DataTablePage dataTablePage) {
        PageInfo pageInfo = aclRoleService.getPageInfoAclRole(dataTablePage);
        return pageInfo;
    }
}


