/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity.enums;

/**
 * Created by Gavin on 2015-11-04 19:36.
 */
public enum PublishAction {
    CREATE_PUBLISH_EVENT("创建发布事件"),
    GET_PUBLISH_SVN("获取发布WAR包"),
    ANALYZE_PROJECT("分析发布工程"),
    GET_SRC_SVN("获取源SVN文件"),
    UPDATE_ETC("更新配置"),
    ETC_APPROVE("配置审核"),

    CREATE_PUBLISH_DIR("创建发布目录"),
    COPY_PUBLISH_OLD_ETC("拷贝原etc文件"),
    COPY_PUBLISH_OLD_WAR("拷贝原war文件"),
    PUBLISH_NEW_ETC("发布新etc文件"),
    PUBLISH_NEW_WAR("发布新war文件"),

    STOP_TOMCAT("停止Tomcat"),
    CHANGE_LN("更改文件指向"),
    START_TOMCAT("启动Tomcat"),

    CLEAN_HISTORY_DIR("清除历史发布目录"),
    UPDATE_SRC_SVN("更新到源SVN"),

    CLEAN_FAIL_DIR("清除失败发布目录"),

    CLEAN_PUBLISH_DIR("清除临时发布目录");

    private String description;

    private PublishAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
