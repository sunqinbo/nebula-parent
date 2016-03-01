/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.web.filter;

import com.olymtech.nebula.core.googleauth.GoogleAuthFactory;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.entity.enums.LoginCodeError;
import com.olymtech.nebula.service.IGoogleTotpAuthService;
import com.olymtech.nebula.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Gavin on 2016-02-29 15:58.
 */
public class TotpCodeValidateFilter extends AccessControlFilter {

    @Autowired
    private IUserService userService;
    @Autowired
    private IGoogleTotpAuthService googleTotpAuthService;

    private boolean totpCodeEnable = true;

    private String totpCodeParam = "totpCode";

    private String usernameParam = "username";

    private String passwordParam = "password";

    private String failureKeyAttribute = "shiroLoginFailure";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws AuthenticationException {
        //1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
        request.setAttribute("totpCodeEnable", totpCodeEnable);

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);

        //2、判断验证码是否禁用 或不是表单提交（允许访问）
        if (totpCodeEnable == false || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }

        String username = httpServletRequest.getParameter(usernameParam);
        String password = httpServletRequest.getParameter(passwordParam);
        String totpCodeString = httpServletRequest.getParameter(totpCodeParam);
        //3、此时是表单提交，验证验证码是否正确
        NebulaUserInfo userInfo = userService.findByUsername(username);
        if(userInfo == null){
            request.setAttribute(failureKeyAttribute, LoginCodeError.LOGIN_UNKNOWN_ACCOUNT);
            return false;
        }

        if(userInfo.getIsEnable()==0) {
            request.setAttribute(failureKeyAttribute, LoginCodeError.LOGIN_USER_UNENABLE);
            return false;
        }

        /** 没有绑定google二次验证 */
        if(!userInfo.getgIsVerify()){
            request.setAttribute(failureKeyAttribute, LoginCodeError.TOTP_UNBINDING);
            return false;
        }

        Integer totpCode = 123456;
        try{
            totpCode = Integer.parseInt(totpCodeString);
        }catch (Exception e){
            request.setAttribute(failureKeyAttribute, LoginCodeError.TOTP_CODE_VERIFY_ERROR);
            return false;
        }
        Boolean codeVerify = GoogleAuthFactory.authoriseUser(username, totpCode);
        if(!codeVerify){
            request.setAttribute(failureKeyAttribute, LoginCodeError.TOTP_CODE_VERIFY_ERROR);
            return false;
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //如果验证码失败了，存储失败key属性
//        request.setAttribute(failureKeyAttribute, "totpCode.error");
        return true;
    }

    public boolean isTotpCodeEnable() {
        return totpCodeEnable;
    }

    public void setTotpCodeEnable(boolean totpCodeEnable) {
        this.totpCodeEnable = totpCodeEnable;
    }

    public String getTotpCodeParam() {
        return totpCodeParam;
    }

    public void setTotpCodeParam(String totpCodeParam) {
        this.totpCodeParam = totpCodeParam;
    }

    public String getFailureKeyAttribute() {
        return failureKeyAttribute;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    public String getUsernameParam() {
        return usernameParam;
    }

    public void setUsernameParam(String usernameParam) {
        this.usernameParam = usernameParam;
    }

    public String getPasswordParam() {
        return passwordParam;
    }

    public void setPasswordParam(String passwordParam) {
        this.passwordParam = passwordParam;
    }
}
