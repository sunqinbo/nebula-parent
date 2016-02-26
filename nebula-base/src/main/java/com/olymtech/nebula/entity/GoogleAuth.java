/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

import java.util.List;

/**
 * Created by Gavin on 2016-02-25 16:48.
 */
public class GoogleAuth {

    private String username;

    private String secret;

    private String otpAuthURL;

    private List<Integer> scratchCodes;

    private String label;

    private String bu;

    public GoogleAuth(){

    }

    public GoogleAuth(String username,
                      String secret,
                      String otpAuthURL,
                      List<Integer> scratchCodes,
                      String label,
                      String bu){
        this.username = username;
        this.secret = secret;
        this.otpAuthURL = otpAuthURL;
        this.scratchCodes = scratchCodes;
        this.label = label;
        this.bu = bu;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getOtpAuthURL() {
        return otpAuthURL;
    }

    public void setOtpAuthURL(String otpAuthURL) {
        this.otpAuthURL = otpAuthURL;
    }

    public List<Integer> getScratchCodes() {
        return scratchCodes;
    }

    public void setScratchCodes(List<Integer> scratchCodes) {
        this.scratchCodes = scratchCodes;
    }
}
