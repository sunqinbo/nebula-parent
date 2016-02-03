package com.olymtech.nebula.entity;

import com.aliyuncs.slb.model.v20140515.DescribeHealthStatusResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerAttributeResponse;

public class NebulaPublishSlb extends BaseDO {
    private Integer publishEventId;

    private Integer publishModuleId;

    private String loadBalancerId;

    private String loadBalancerName;

    private String loadBalancerAddress;

    private String loadBalancerStatus;

    private String regionId;

    private String aliyunAccount;

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAliyunAccount() {
        return aliyunAccount;
    }

    public void setAliyunAccount(String aliyunAccount) {
        this.aliyunAccount = aliyunAccount;
    }

    private DescribeLoadBalancerAttributeResponse describeLoadBalancerAttributeResponse;

    private DescribeHealthStatusResponse describeHealthStatusResponse;

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

    public DescribeLoadBalancerAttributeResponse getDescribeLoadBalancerAttributeResponse() {
        return describeLoadBalancerAttributeResponse;
    }

    public void setDescribeLoadBalancerAttributeResponse(DescribeLoadBalancerAttributeResponse describeLoadBalancerAttributeResponse) {
        this.describeLoadBalancerAttributeResponse = describeLoadBalancerAttributeResponse;
    }

    public DescribeHealthStatusResponse getDescribeHealthStatusResponse() {
        return describeHealthStatusResponse;
    }

    public void setDescribeHealthStatusResponse(DescribeHealthStatusResponse describeHealthStatusResponse) {
        this.describeHealthStatusResponse = describeHealthStatusResponse;
    }
}