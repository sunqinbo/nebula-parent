package com.olymtech.nebula.entity;

public class NebulaPublishSlb extends BaseDO {
    private Integer id;

    private Integer publishEventId;

    private Integer publishModuleId;

    private String loadBalancerId;

    private String loadBalancerName;

    private String loadBalancerAddress;

    private String loadBalancerStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPublishEventId() {
        return publishEventId;
    }

    public void setPublishEventId(Integer publishEventId) {
        this.publishEventId = publishEventId;
    }

    public Integer getPublishModuleId() {
        return publishModuleId;
    }

    public void setPublishModuleId(Integer publishModuleId) {
        this.publishModuleId = publishModuleId;
    }

    public String getLoadBalancerId() {
        return loadBalancerId;
    }

    public void setLoadBalancerId(String loadBalancerId) {
        this.loadBalancerId = loadBalancerId == null ? null : loadBalancerId.trim();
    }

    public String getLoadBalancerName() {
        return loadBalancerName;
    }

    public void setLoadBalancerName(String loadBalancerName) {
        this.loadBalancerName = loadBalancerName == null ? null : loadBalancerName.trim();
    }

    public String getLoadBalancerAddress() {
        return loadBalancerAddress;
    }

    public void setLoadBalancerAddress(String loadBalancerAddress) {
        this.loadBalancerAddress = loadBalancerAddress == null ? null : loadBalancerAddress.trim();
    }

    public String getLoadBalancerStatus() {
        return loadBalancerStatus;
    }

    public void setLoadBalancerStatus(String loadBalancerStatus) {
        this.loadBalancerStatus = loadBalancerStatus == null ? null : loadBalancerStatus.trim();
    }
}