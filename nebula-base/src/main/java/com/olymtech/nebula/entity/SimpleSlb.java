package com.olymtech.nebula.entity;

/**
 * Created by wangyiqiang on 16/2/3.
 */
public class SimpleSlb extends BaseDO{
    private String loadBalancerId;

    private String loadBalancerName;

    private String loadBalancerAddress;

    private String loadBalancerStatus;

    private String regionId;

    private String aliyunAccount;

    public String getLoadBalancerId() {
        return loadBalancerId;
    }

    public void setLoadBalancerId(String loadBalancerId) {
        this.loadBalancerId = loadBalancerId;
    }

    public String getLoadBalancerName() {
        return loadBalancerName;
    }

    public void setLoadBalancerName(String loadBalancerName) {
        this.loadBalancerName = loadBalancerName;
    }

    public String getLoadBalancerAddress() {
        return loadBalancerAddress;
    }

    public void setLoadBalancerAddress(String loadBalancerAddress) {
        this.loadBalancerAddress = loadBalancerAddress;
    }

    public String getLoadBalancerStatus() {
        return loadBalancerStatus;
    }

    public void setLoadBalancerStatus(String loadBalancerStatus) {
        this.loadBalancerStatus = loadBalancerStatus;
    }

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
}
