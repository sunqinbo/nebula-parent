package com.olymtech.nebula.controller;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		} else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void controller(HttpServletResponse response, Integer id) throws Exception {
        switch(id) {
            case 1:
                throw new Exception("controller1");
            case 2:
                throw new Exception("controller2");
            case 3:
                throw new Exception("controller3");
            case 4:
                throw new Exception("controller4");
            case 5:
                throw new Exception("controller5");
            default:
                throw new Exception("controller6");
        }
    }



}
