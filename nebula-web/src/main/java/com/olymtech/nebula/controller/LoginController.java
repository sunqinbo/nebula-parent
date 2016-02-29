package com.olymtech.nebula.controller;

import com.olymtech.nebula.core.googleauth.GoogleAuthFactory;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.GoogleAuth;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.entity.enums.LoginCodeError;
import com.olymtech.nebula.service.IUserService;
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
        String exceptionClassName = req.getAttribute("shiroLoginFailure").toString();
        String error = null;

        /**
         * 这里会返回信息
         * Exception 类名称 一般由原生shiro产生
         * LoginCodeError 枚举 一般由 com.olymtech.nebula.web.filter.TotpCodeValidateFilter 产生
         */
        try{
            LoginCodeError loginCodeError = LoginCodeError.valueOf(exceptionClassName);
            error = loginCodeError.getDescription();
        }catch (Exception e){
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                error = LoginCodeError.LOGIN_UNKNOWN_ACCOUNT.getDescription();
            } else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
                error = LoginCodeError.LOGIN_USER_UNENABLE.getDescription();
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                error = LoginCodeError.LOGIN_USER_PASSWORD_ERROR.getDescription();
            } else if (AuthenticationException.class.getName().equals(exceptionClassName)) {
                error = LoginCodeError.LOGIN_USER_PASSWORD_ERROR.getDescription();
            } else if (GoogleAuthAccountException.class.getName().equals(exceptionClassName)) {
                error = LoginCodeError.TOTP_UNBINDING.getDescription();
            } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
                error = LoginCodeError.LOGIN_RETRY_LIMIT.getDescription();
            } else if (exceptionClassName != null) {
                error = "其他错误：" + exceptionClassName;
            }
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
    public String bindingCode() {
        return "bindingCode";
    }

    @RequestMapping(value = "/bindingCode/credentials", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object createCredentialsForUser(String username, String password) {

//        SpringUtils.getBean(CredentialRepositoryImpl.class);

        NebulaUserInfo userInfo = userService.findByUsername(username);
        if (userInfo == null) {
            return returnCallback("Error", "账号不存在");
        }

        if (!StringUtils.isNotEmpty(password)) {
            return returnCallback("Error", "密码不能为空");
        }

        Boolean result = passwordHelper.verifyAccount(userInfo.getUsername(), password);
        if (!result) {
            /** session重试次数，5次限制，需要增加 */
            //TODO
            return returnCallback("Error", "密码错误");
        }

        /** userInfo.gIsVerify = true : 已通过验证，不能再生成二维码 */
        if (userInfo.getgIsVerify()) {
            return returnCallback("Error", "您已经绑定身份验证器。");
        }

        GoogleAuth googleAuth = GoogleAuthFactory.createCredentialsForUser(username);

        if (googleAuth != null) {
            return returnCallback("Success", googleAuth);
        } else {
            return returnCallback("Error", "创建二维码失败");
        }
    }

    @RequestMapping(value = "/bindingCode/gCodesVerifyAndBinding", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object gCodesVerifyAndBinding(String username, String codeFirstString, String codeSecondString) {

        if (StringUtils.isEmpty(codeFirstString) || StringUtils.isEmpty(codeSecondString)) {
            return returnCallback("Error", "动态验证码不能为空。");
        }

        int codeFirst = Integer.parseInt(codeFirstString);
        int codeSecond = Integer.parseInt(codeSecondString);

        Boolean codeFirstVerify = GoogleAuthFactory.authoriseUser(username, codeFirst);
        Boolean codeSecondVerify = GoogleAuthFactory.authoriseUser(username, codeSecond);

        /** 验证码check */
        if (!codeFirstVerify || !codeSecondVerify) {
            return returnCallback("Error", "动态验证，验证错误。");
        }

        /** 绑定 gIsVerify 设置true */
        NebulaUserInfo userInfo = userService.findByUsername(username);
        userInfo.setgIsVerify(true);
        userService.updateByIdSelective(userInfo);
        return returnCallback("Success", "动态验证，验证错误。");

    }

    @RequestMapping(value = "/unBindingCode", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object unBingdingCode(Integer empId) {
        if (empId != null) {
            NebulaUserInfo nebulaUserInfo = userService.selectByEmpId(empId);
            nebulaUserInfo.setgIsVerify(false);
            userService.updateByIdSelective(nebulaUserInfo);
            return returnCallback("Success", "账号解绑成功。");
        }
        return returnCallback("Error", "账号解绑失败,请重试。");
    }
}
