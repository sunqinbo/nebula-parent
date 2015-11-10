package com.olymtech.nebula.entity;

public class NebulaUserInfo extends BaseDO{

    private String username;

    private String mobilePhone;

    private String weixinAcc;

    private String qqAcc;

    private String email;

    private String nickname;

    private String deptName;

    private String jobTitle;

    private Integer empId;

    private Integer supervisorEmpId;

    private Integer isEnable;

    private String password;

    private String securityKey;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone == null ? null : mobilePhone.trim();
    }

    public String getWeixinAcc() {
        return weixinAcc;
    }

    public void setWeixinAcc(String weixinAcc) {
        this.weixinAcc = weixinAcc == null ? null : weixinAcc.trim();
    }

    public String getQqAcc() {
        return qqAcc;
    }

    public void setQqAcc(String qqAcc) {
        this.qqAcc = qqAcc == null ? null : qqAcc.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle == null ? null : jobTitle.trim();
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public Integer getSupervisorEmpId() {
        return supervisorEmpId;
    }

    public void setSupervisorEmpId(Integer supervisorEmpId) {
        this.supervisorEmpId = supervisorEmpId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey == null ? null : securityKey.trim();
    }

    public String getCredentialsSalt() {
        return username + securityKey;
    }
}