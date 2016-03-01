package com.olymtech.nebula.entity;

public class GoogleTotpAuth extends BaseDO {

    private Integer empId;

    private String gSecret;

    private String gOptAuthUrl;

    private String gScratchCodes;

    public GoogleTotpAuth(){

    }

    public GoogleTotpAuth(Integer empId,
                          String gSecret,
                          String gOptAuthUrl,
                          String gScratchCodes){
        this.empId = empId;
        this.gSecret = gSecret;
        this.gOptAuthUrl = gOptAuthUrl;
        this.gScratchCodes = gScratchCodes;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getgSecret() {
        return gSecret;
    }

    public void setgSecret(String gSecret) {
        this.gSecret = gSecret == null ? null : gSecret.trim();
    }

    public String getgOptAuthUrl() {
        return gOptAuthUrl;
    }

    public void setgOptAuthUrl(String gOptAuthUrl) {
        this.gOptAuthUrl = gOptAuthUrl == null ? null : gOptAuthUrl.trim();
    }

    public String getgScratchCodes() {
        return gScratchCodes;
    }

    public void setgScratchCodes(String gScratchCodes) {
        this.gScratchCodes = gScratchCodes == null ? null : gScratchCodes.trim();
    }
}