/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

/**
 * Created by Gavin on 2015-11-02 18:21.
 */
public class SimpleHost {

    private String hostName;

    private String hostIp;

    private String hostInstanceId;

    private Integer batchTag;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getHostInstanceId() {
        return hostInstanceId;
    }

    public void setHostInstanceId(String hostInstanceId) {
        this.hostInstanceId = hostInstanceId;
    }

    public Integer getBatchTag() {
        return batchTag;
    }

    public void setBatchTag(Integer batchTag) {
        this.batchTag = batchTag;
    }
}
