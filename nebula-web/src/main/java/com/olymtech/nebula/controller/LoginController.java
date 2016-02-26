package com.olymtech.nebula.controller;

import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by taoshanchang on 2015/11/3.
 */
@Controller
@RequestMapping("/")
public class LoginController extends BaseController {
    @Resource
    private IUserService userService;

    @RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, Model model) {
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "该用户不存在";
        } else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
            error = "该用户未启用";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (AuthenticationException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "密码尝试次数过多，锁定5分钟，请稍后尝试";
        } else if (exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping(value = "/test")
    //@RequiresPermissions("user:add")
    public Callback test(HttpServletRequest request, HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        //subject.checkPermissions("user:update,delete");
        subject.checkPermissions("user:add");
        //throw new NullPointerException("xxxx");

        System.out.println("test");
        return null;
    }

    @RequestMapping(value = "/test2")
    public String test2(HttpServletRequest request, HttpServletResponse response) {
        throw new NullPointerException("xxxx");
    }

    @RequestMapping(value = "/bindingCode/dynamic.htm")
    public String bindingCode(){
        return "bindingCode";
    }

}
