package com.olymtech.nebula.controller;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.AclRole;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.Select2Data;
import com.olymtech.nebula.service.IAclRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liwenji on 2015/11/17.
 */
@Controller
@RequestMapping("/orle")
public class RoleController extends BaseController {

    @Resource
    private IAclRoleService aclRoleService;

    @RequestMapping(value="/jurisdiction.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String jurisdiction (){
        return "role/jurisdiction";
    }


    @RequestMapping(value="/roleList.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String roleList (){
        return "role/roleList";
    }

    @RequestMapping(value="/createRole.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String createRole (){
        return "role/createRole";
    }

    @RequestMapping(value="/editRole.html",method= {RequestMethod.POST,RequestMethod.GET})
    public String editRole(Model model,Integer id){
        model.addAttribute("edit",true);
        model.addAttribute("id",id);
        return "role/createRole";
    }

    @RequestMapping(value = "/insertAclRole.htm", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback insertAclRole(AclRole aclRole, @RequestParam("permissionIds[]") List<Integer> permissionIds) {
        aclRoleService.insertAclRole(aclRole, permissionIds);
        return returnCallback("Success", "插入角色成功");
    }

    @RequestMapping(value = "/deleteAclRole.htm", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback deleteAclRole(Integer id) {
        aclRoleService.deleteAclRoleById(id);
        return returnCallback("Success", "删除角色成功");
    }

    @RequestMapping(value = "/updateAclRole.htm", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Callback updateAclRole(AclRole aclRole, @RequestParam("permissionIds[]") List<Integer> permissionIds) {
        aclRoleService.updateAclRole(aclRole, permissionIds);
        return returnCallback("Success", "更新角色成功");
    }

    @RequestMapping(value = "/selectAllPagingRole", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object selectAllPagingRole(DataTablePage dataTablePage) {
        PageInfo pageInfo = aclRoleService.getPageInfoAclRole(dataTablePage);
        return pageInfo;
    }

    @RequestMapping(value = "/getAclRoleWithPermissionsByRoleId", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object getAclRoleWithPermissionsByRoleId(Integer roleId) {
        return aclRoleService.getAclRoleWithPermissionsByRoleId(roleId);
    }

    @RequestMapping(value = "/selectRole", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object selectPermission() {
        List<Select2Data> select2Datas =aclRoleService.getAllRoles();
        return select2Datas;
    }

}
