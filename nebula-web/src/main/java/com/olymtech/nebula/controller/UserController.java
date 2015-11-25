package com.olymtech.nebula.controller;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.service.IUserService;
import com.olymtech.nebula.web.utils.PasswordHelper;
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

    @RequestMapping(value="/add.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String createUser (){
        return "user/createUser";
    }

    @RequestMapping(value="/list.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String userList (){
        return "user/userList";
    }


    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback insertUser(NebulaUserInfo userInfo,@RequestParam("roleIds[]") Integer[] roleIds) {
        try {
            passwordHelper.encryptPassword(userInfo);
            userService.insertNebulaUserInfo(userInfo,roleIds);
            return returnCallback("Success", "插入用户成功");
        } catch (Exception e) {
            logger.error("insertUser error:", e);
        }
        return returnCallback("Error", "插入用户失败");
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
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

    @RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback updateUser(NebulaUserInfo userInfo,@RequestParam("roleIds[]")List<Integer> roleIds ) {
        try {
            userService.updateNebulaUserInfo(userInfo,roleIds);
            return returnCallback("Success", "更新用户成功");
        } catch (Exception e) {
            logger.error("updateUser error:", e);
        }
        return returnCallback("Error","更新用户失败");
    }

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

    @RequestMapping(value="/update.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String editUser(Model model,Integer empId,Integer id){
        model.addAttribute("edit",true);
        model.addAttribute("empId",empId);
        model.addAttribute("id",id);
        return "user/createUser";
    }
}
