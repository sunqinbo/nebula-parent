package com.olymtech.nebula.controller;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.AclPermission;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.zNode;
import com.olymtech.nebula.service.IAclPermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WYQ on 2015/11/17.
 */
@Controller("permissionController")
@RequestMapping("/")
public class AclPermissionController extends BaseController {

    @Resource
    private IAclPermissionService permissionService;

    @RequestMapping(value = "/permission/insertAclPermission.htm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback insertAclPermission(AclPermission permission) {
        try {
            permissionService.insertAclPermission(permission);
            return returnCallback("Success", "插入权限成功");
        } catch (Exception e) {
            logger.error("insertAclPermission error:", e);
        }
        return returnCallback("Error", "插入权限失败");
    }

    @RequestMapping(value = "/permission/deleteAclPermission.htm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback deleteAclPermission(Integer id) {
        try {
            permissionService.deleteAclPermissionById(id);
            return returnCallback("Success", "删除权限成功");
        } catch (Exception e) {
            logger.error("deleteAclPermission error:", e);
        }
        return returnCallback("Error", "删除权限失败");
    }

    @RequestMapping(value = "/permission/updateAclPermission.htm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback updateAclPermission(AclPermission permission) {
        try {
            permissionService.updateAclPermission(permission);
            return returnCallback("Success", "更新权限成功");
        }catch (Exception e) {
            logger.error("updateAclPermission error:", e);
        }
        return returnCallback("Error","更新权限失败");
    }

    @RequestMapping(value = "/permission/selectAllPagingPermission", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object selectAllPagingPermission(DataTablePage dataTablePage) {
        PageInfo pageInfo = permissionService.getPageInfoAclPermission(dataTablePage);
        return pageInfo;
    }

    @RequestMapping(value = "/permission/selectPermission", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object selectPermission() {
        List<AclPermission> aclPermissions=permissionService.getPermissions();
        List<zNode> zNodes=new ArrayList<>();
        for (AclPermission aclPermission:aclPermissions) {
            zNode znode =new zNode();
            znode.setName(aclPermission.getPermissionCname());
            znode.setId(aclPermission.getId());
            znode.setPid(aclPermission.getPid());
            znode.setOpen(true);
            zNodes.add(znode);
        }
        return zNodes;
    }

}


