package com.olymtech.nebula.controller;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by WYQ on 2015/11/18.
 */
@Controller("userController")
@RequestMapping("/")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/user/insertUser.htm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback insertUser(NebulaUserInfo userInfo) {
        try {
            userService.insertNebulaUserInfo(userInfo);
            return returnCallback("Success", "插入用户成功");
        } catch (Exception e) {
            logger.error("insertUser error:", e);
        }
        return returnCallback("Error", "插入用户失败");
    }

    @RequestMapping(value = "/user/deleteUser.htm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback deleteUser(Integer id) {
        try {
            userService.deleteNebulaUserInfo(id);
            return returnCallback("Success", "删除用户成功");
        } catch (Exception e) {
            logger.error("deleteUser error:", e);
        }
        return returnCallback("Error","删除用户失败");
    }

    @RequestMapping(value = "/user/updateUser.htm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback updateUser(NebulaUserInfo userInfo) {
        try {
            userService.updateNebulaUserInfo(userInfo);
            return returnCallback("Success", "更新用户成功");
        } catch (Exception e) {
            logger.error("updateUser error:", e);
        }
        return returnCallback("Error","更新用户失败");
    }

    @RequestMapping(value = "/user/selectAllPagingUser", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object selectAllPagingUser(DataTablePage dataTablePage) {
        PageInfo pageInfo = userService.getPageInfoAclUser(dataTablePage);
        return pageInfo;
    }

}
