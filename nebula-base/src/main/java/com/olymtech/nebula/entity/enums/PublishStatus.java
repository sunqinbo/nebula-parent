/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity.enums;

/**
 * Created by Gavin on 2015-11-25 11:40.
 */
public enum PublishStatus {
    PENDING_APPROVE("待审批"),
    PENDING_PUBLISH("待发布"),
    PUBLISHING("发布中"),
    PUBLISHED("已发布"),
    ROLLBACK("已回滚");

    private String description;

    private PublishStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
