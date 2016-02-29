package com.olymtech.nebula.controller;

import com.olymtech.nebula.core.googleauth.GoogleAuthFactory;
import com.olymtech.nebula.core.utils.SpringUtils;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.GoogleAuth;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.service.IUserService;
import com.olymtech.nebula.service.action.GetPublishSvnAction;
import com.olymtech.nebula.service.googleauth.CredentialRepositoryImpl;
import com.olymtech.nebula.service.utils.PasswordHelper;
import com.olymtech.nebula.web.exception.GoogleAuthAccountException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Resource
    private PasswordHelper passwordHelper;

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
        } else if (GoogleAuthAccountException.class.getName().equals(exceptionClassName)) {
            error = "动态验证设备未绑定，请绑定动态验证码";
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

    @RequestMapping(value = "/bindingCode/credentials", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object createCredentialsForUser(String username, String password){

//        SpringUtils.getBean(CredentialRepositoryImpl.class);

        NebulaUserInfo userInfo = userService.findByUsername(username);
        if(userInfo == null){
            return returnCallback("Error", "账号不存在");
        }

        if(!StringUtils.isNotEmpty(password)){
            return returnCallback("Error", "密码不能为空");
        }

        Boolean result = passwordHelper.verifyAccount(userInfo.getUsername(), password);
        if(!result){
            /** session重试次数，5次限制，需要增加 */
            //TODO
            return returnCallback("Error", "密码错误");
        }

        /** userInfo.gIsVerify = true : 已通过验证，不能再生成二维码 */
        if(userInfo.getgIsVerify()){
            return returnCallback("Error", "您已经绑定身份验证器。");
        }

        GoogleAuth googleAuth = GoogleAuthFactory.createCredentialsForUser(username);



        if(googleAuth != null){
            return returnCallback("Success", googleAuth);
        }else{
            return returnCallback("Error", "创建二维码失败");
        }
    }

    @RequestMapping(value = "/bindingCode/gCodesVerifyAndBinding", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object gCodesVerifyAndBinding(String username, String codeFirstString, String codeSecondString){

        if(StringUtils.isEmpty(codeFirstString) || StringUtils.isEmpty(codeSecondString)){
            return returnCallback("Error", "动态验证码不能为空。");
        }

        int codeFirst = Integer.parseInt(codeFirstString);
        int codeSecond = Integer.parseInt(codeSecondString);

        Boolean codeFirstVerify = GoogleAuthFactory.authoriseUser(username, codeFirst);
        Boolean codeSecondVerify = GoogleAuthFactory.authoriseUser(username, codeSecond);

        /** 验证码check */
        if(!codeFirstVerify || !codeSecondVerify){
            return returnCallback("Error", "动态验证，验证错误。");
        }

        /** 绑定 gIsVerify 设置true */
        NebulaUserInfo userInfo = userService.findByUsername(username);
        userInfo.setgIsVerify(true);
        userService.updateByIdSelective(userInfo);
        return returnCallback("Success", "动态验证，验证错误。");

    }

}
