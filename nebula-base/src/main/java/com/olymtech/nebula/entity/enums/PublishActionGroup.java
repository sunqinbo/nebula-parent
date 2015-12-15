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
    SUCCESS_END("发布成功收尾"),
    FAIL_END("发布失败收尾"),
    RESTART_TOMCAT("重启tomcat"),
    CLEAN_END("清理发布目录");

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
