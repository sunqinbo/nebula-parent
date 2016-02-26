package com.olymtech.nebula.controller;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.core.googleauth.GoogleAuthFactory;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.service.IAnalyzeArsenalApiService;
import com.olymtech.nebula.service.IUserService;
import com.olymtech.nebula.service.utils.PasswordHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Resource
    private CacheManager cacheManager;

    @Resource
    private IAnalyzeArsenalApiService analyzeArsenalApiService;

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
        if (userInfo.getEmpId() == null) {
            return returnCallback("Error", "新增用户工号为空");
        }
        NebulaUserInfo userInfoInDB = userService.selectByEmpId(userInfo.getEmpId());

        if (userInfoInDB != null) {
            return returnCallback("Error", "新增用户失败：工号 " + userInfo.getEmpId() + " 已存在");
        }

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
        NebulaUserInfo nebulaUserInfo = userService.selectById(userId);;
        //更新密码重试缓存
        Cache<String, AtomicInteger> passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        passwordRetryCache.remove(nebulaUserInfo.getUsername());
        Cache<String, AtomicInteger> authorizationCache = cacheManager.getCache("authorizationCache");
        authorizationCache.remove(nebulaUserInfo.getUsername());
        Cache<String, AtomicInteger> authenticationCache = cacheManager.getCache("authenticationCache");
        authenticationCache.remove(nebulaUserInfo.getUsername());
        return returnCallback("Success", "更新用户密码成功");
    }

    @RequestMapping(value = "/update/myPassword", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback updateMyPassword(String oldPassword, String newPassword, String repeatPassword) {
        NebulaUserInfo userInfo = getLoginUser();
        Boolean result = passwordHelper.verifyAccount(userInfo.getUsername(), oldPassword);
        if (result == true) {
            returnCallback("PasswordRight", "密码输入正确");
            if (newPassword.equals(repeatPassword)) {
                userInfo.setPassword(newPassword);
                passwordHelper.encryptPassword(userInfo);
                Cache<String, AtomicInteger> passwordRetryCache = cacheManager.getCache("passwordRetryCache");
                passwordRetryCache.remove(userInfo.getUsername());
                userService.updateMyPassword(userInfo);
                return returnCallback("Success", "更新密码成功");
            } else {
                return returnCallback("Error", "两次输入密码不一致");
            }
        }
        return returnCallback("Error", "密码不正确");
    }

    @RequiresPermissions("user:page")
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object selectAllPagingUser(DataTablePage dataTablePage, NebulaUserInfo nebulaUserInfo) {
        /** like username nickname 两个字段 */
        if(StringUtils.isNotEmpty(nebulaUserInfo.getNickname())){
            nebulaUserInfo.setUsername(nebulaUserInfo.getNickname());
        }
        PageInfo pageInfo = userService.getPageInfoAclUser(dataTablePage,nebulaUserInfo);
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
        List<ProductTree> productTrees = analyzeArsenalApiService.getBuProductTreeList();
        model.addAttribute("productTrees", productTrees);
        model.addAttribute("edit", true);
        model.addAttribute("empId", empId);
        model.addAttribute("id", id);
        return "user/createUser";
    }

    @RequestMapping(value = "/update/myPassword.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String updateMyPassword() {
        return "user/updatePassword";
    }

}
