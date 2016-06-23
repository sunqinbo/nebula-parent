/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

/**
 * Created by Gavin on 2016-06-23 16:29.
 */
public class NginxServer {

    private Integer index;

    private String upstream;

    private String name;

    private String status;

    private Long rise;

    private Integer fall;

    private String type;

    private Integer port;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getUpstream() {
        return upstream;
    }

    public void setUpstream(String upstream) {
        this.upstream = upstream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRise() {
        return rise;
    }

    public void setRise(Long rise) {
        this.rise = rise;
    }

    public Integer getFall() {
        return fall;
    }

    public void setFall(Integer fall) {
        this.fall = fall;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
