/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity.enums;

/**
 * Created by Gavin on 2015-11-10 09:32.
 */
public enum PublishActionGroup {
    PRE_MASTER("MASTER准备"),
    PRE_MINION("预发布"),
    PUBLISH_REAL("正式发布"),
    SUCCESS_CLEAR("成功清理"),
    FAIL_CLEAR("失败清理");

    private String description;

    private PublishActionGroup(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
