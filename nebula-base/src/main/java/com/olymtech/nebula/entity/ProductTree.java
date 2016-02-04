/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

import java.util.List;

/**
 * Created by Gavin on 2015-11-02 10:32.
 */
public class ProductTree extends BaseDO {

    private String nodeName;

    private String nodeCname;

    private String srcSvn;

    private String publishSvn;

    private Integer pid;

    private Integer treeLevel;

    private List<SimpleHost> hosts;

    private List<String> apps;

    private List<SimpleSlb> slbs;

    public List<SimpleSlb> getSlbs() {
        return slbs;
    }

    public void setSlbs(List<SimpleSlb> slbs) {
        this.slbs = slbs;
    }

    public String getPublishSvn() {
        return publishSvn;
    }

    public void setPublishSvn(String publishSvn) {
        this.publishSvn = publishSvn;
    }

    public Integer getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getNodeCname() {
        return nodeCname;
    }

    public void setNodeCname(String nodeCname) {
        this.nodeCname = nodeCname;
    }

    public String getSrcSvn() {return srcSvn;}

    public void setSrcSvn(String srcSvn) {this.srcSvn = srcSvn;}

    public List<SimpleHost> getHosts() {
        return hosts;
    }

    public void setHosts(List<SimpleHost> hosts) {
        this.hosts = hosts;
    }

    public List<String> getApps() {
        return apps;
    }

    public void setApps(List<String> apps) {
        this.apps = apps;
    }
}
