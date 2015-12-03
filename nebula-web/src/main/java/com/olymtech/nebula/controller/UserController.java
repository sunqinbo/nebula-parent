package com.olymtech.nebula.controller;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.service.IUserService;
import com.olymtech.nebula.service.utils.PasswordHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by WYQ on 2015/11/18.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;

    @Resource
    private PasswordHelper passwordHelper;

    @RequiresPermissions("user:add")
    @RequestMapping(value = "/add.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String createUser() {
        return "user/createUser";
    }

    @RequiresPermissions("user:page")
    @RequestMapping(value = "/list.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String userList() {
        return "user/userList";
    }


    @RequiresPermissions("user:add")
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback insertUser(NebulaUserInfo userInfo, @RequestParam(value = "roleIds[]", required = false) Integer[] roleIds) {
        passwordHelper.encryptPassword(userInfo);
        userService.insertNebulaUserInfo(userInfo, roleIds);
        return returnCallback("Success", "插入用户成功");
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback deleteUser(Integer id) {
        userService.deleteNebulaUserInfo(id);
        return returnCallback("Success", "删除用户成功");
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback updateUser(NebulaUserInfo userInfo, @RequestParam("roleIds[]") List<Integer> roleIds) {
        userService.updateNebulaUserInfo(userInfo, roleIds);
        return returnCallback("Success", "更新用户成功");
    }

    @RequiresPermissions("user:updatePassword")
    @RequestMapping(value = "/update/password", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback updatePassword(Integer userId, String newPassword) {
        userService.updatePassword(userId, newPassword);
        return returnCallback("Success", "更新用户密码成功");
    }

    @RequiresPermissions("user:page")
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object selectAllPagingUser(DataTablePage dataTablePage) {
        PageInfo pageInfo = userService.getPageInfoAclUser(dataTablePage);
        return pageInfo;
    }

    @RequestMapping(value = "/get/empId", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object getAclUserWithRolesByEmpId(Integer empId) {
        return userService.getAclUserWithRolesByEmpId(empId);
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/update.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String editUser(Model model, Integer empId, Integer id) {
        model.addAttribute("edit", true);
        model.addAttribute("empId", empId);
        model.addAttribute("id", id);
        return "user/createUser";
    }
}
