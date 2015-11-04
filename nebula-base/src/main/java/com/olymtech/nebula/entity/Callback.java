/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

/**
 * Created by Gavin on 2015-09-14 15:56.
 */
public class Callback {
    /* 请求返回 状态的信息 */
    private String callbackMsg;
    /* 请求返回 具体内容*/
    private Object responseContext;

    public Callback() {
        super();
    }

    public Callback(String callbackMsg, Object responseContext){
        super();
        this.callbackMsg = callbackMsg;
        this.responseContext = responseContext;
    }

    public String getCallbackMsg() {
        return callbackMsg;
    }

    public void setCallbackMsg(String callbackMsg) {
        this.callbackMsg = callbackMsg;
    }

    public Object getResponseContext() {
        return responseContext;
    }

    public void setResponseContext(Object responseContext) {
        this.responseContext = responseContext;
    }
}
