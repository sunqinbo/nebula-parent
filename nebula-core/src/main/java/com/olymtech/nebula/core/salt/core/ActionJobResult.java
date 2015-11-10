/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt.core;

/**
 * Action result of all minion
 * @author taoshanchang 15/11/10
 */
public class ActionJobResult {

    private String ip;

    private boolean success;

    private String result;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
