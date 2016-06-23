/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity.enums;

/**
 * Created by Gavin on 2016-06-23 11:02.
 */
public enum HostPublishStatus {

    PENDING_PUBLISH("待发布"),
    PUBLISHING("发布中"),
    PUBLISHED("已发布"),
    FAILED("失败");

    private String description;

    private HostPublishStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
