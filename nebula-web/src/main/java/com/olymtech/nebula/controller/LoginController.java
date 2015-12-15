package com.olymtech.nebula.controller;

import com.olymtech.nebula.entity.Callback;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by taoshanchang on 2015/11/3.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    @RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, Model model) {
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        }else if (AuthenticationException.class.getName().equals(exceptionClassName)) {
        	error = "用户名/密码错误";
		} else if(ExcessiveAttemptsException.class.getName().equals(exceptionClassName)){
            error = "请求次数过多，用户被锁定";
        }else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping(value = "/test")
    //@RequiresPermissions("user:add")
    public Callback test(HttpServletRequest request, HttpServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        //subject.checkPermissions("user:update,delete");
        subject.checkPermissions("user:add");
        //throw new NullPointerException("xxxx");

        System.out.println("test");
        return null;
    }

    @RequestMapping(value = "/test2")
    public String test2(HttpServletRequest request, HttpServletResponse response){
        throw new NullPointerException("xxxx");
    }

}
