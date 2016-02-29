/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity.enums;

/**
 * Created by Gavin on 2016-02-29 17:02.
 */
public enum LoginCodeError {
    LOGIN_UNKNOWN_ACCOUNT("该用户不存在"),
    LOGIN_USER_UNENABLE("该用户未启用"),
    LOGIN_USER_PASSWORD_ERROR("用户名/密码错误"),
    LOGIN_RETRY_LIMIT("密码尝试次数过多，锁定5分钟，请稍后尝试"),
    TOTP_UNBINDING("动态验证设备未绑定，请绑定动态验证码"),
    TOTP_CODE_VERIFY_ERROR("动态验证码无效"),
    PUBLISHED("已发布"),
    ROLLBACK("已回滚"),
    CANCEL("已取消");

    private String description;

    private LoginCodeError(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
