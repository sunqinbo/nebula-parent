package com.olymtech.nebula.controller;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.AclPermission;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.zNode;
import com.olymtech.nebula.service.IAclPermissionService;
import com.olymtech.nebula.service.IAclRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYQ on 2015/11/17.
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Resource
    private IAclPermissionService permissionService;

    @Resource
    private IAclRoleService aclRoleService;

    @RequiresPermissions("permission:page")
    @RequestMapping(value = "/list.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String jurisdiction() throws Exception {
        return "permission/permissionList";
    }

    @RequiresPermissions("permission:add")
    @RequestMapping(value = "/add.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String createPermission() throws Exception {
        return "permission/createPermission";
    }

    @RequiresPermissions("permission:add")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback insertAclPermission(AclPermission permission) throws Exception {
        permissionService.insertAclPermission(permission);
        return returnCallback("Success", "插入权限成功");
    }

    @RequiresPermissions("permission:delete")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback deleteAclPermission(Integer id) throws Exception {
        permissionService.deleteAclPermissionById(id);
        return returnCallback("Success", "删除权限成功");
    }

    @RequiresPermissions("permission:update")
    @RequestMapping(value="/update.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String editPermission(Model model,Integer id){
        model.addAttribute("edit",true);
        model.addAttribute("id",id);
        return "permission/createPermission";
    }

    @RequiresPermissions("permission:update")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback updateAclPermission(AclPermission permission) throws Exception {
        permissionService.updateAclPermission(permission);
        return returnCallback("Success", "更新权限成功");
    }

    @RequiresPermissions("permission:page")
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object selectAllPagingPermission(DataTablePage dataTablePage) throws Exception {
        PageInfo pageInfo = permissionService.getPageInfoAclPermission(dataTablePage);
        return pageInfo;
    }

    @RequestMapping(value = "/selectPermission", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object selectPermission(Integer id) throws Exception {
        List<AclPermission> permissionList = new ArrayList<>();
        if (id != null) {
            permissionList = aclRoleService.getAclRoleWithPermissionsByRoleId(id).getAclPermissions();
        }
        List<zNode> zNodes = permissionService.getzNodes(permissionList);
        return zNodes;
    }

    @RequestMapping(value = "/getList/noPid", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object selectPermissionsWithNoPid() {
        List<AclPermission> permissionList = new ArrayList<>();
        permissionList = permissionService.getPermissionsWithNoPid();
        return permissionList;
    }

    @RequestMapping(value = "/get/permissionId", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object getPermissionById(Integer permissionId) {
        return permissionService.getPermissionById(permissionId);
    }

}


