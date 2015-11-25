/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.controller;

import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.web.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gavin on 2015-09-14 15:50.
 */
public class BaseController {
    protected Logger logger           = LoggerFactory.getLogger(this.getClass());

    protected Callback callback = new Callback();
    protected Map<String, Object> callbackMap = new HashMap<String, Object>();
    protected String    callBackMsg;
    protected Object    responseContext;
    @Resource
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public Callback returnCallback(String callBackMsg, Object responseContext){
        callback.setCallbackMsg(callBackMsg);
        callback.setResponseContext(responseContext);
        return callback;
    }

    public NebulaUserInfo getLoginUser(){
        NebulaUserInfo nebulaUserInfo = (NebulaUserInfo) getRequest().getAttribute(Constants.CURRENT_USER);
        return nebulaUserInfo;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public String getCallBackMsg() {
        return callBackMsg;
    }

    public void setCallBackMsg(String callBackMsg) {
        this.callBackMsg = callBackMsg;
    }

    public Object getResponseContext() {
        return responseContext;
    }

    public void setResponseContext(Object responseContext) {
        this.responseContext = responseContext;
    }

    public Map<String, Object> getCallbackMap() {
        return callbackMap;
    }

    public void setCallbackMap(Map<String, Object> callbackMap) {
        this.callbackMap = callbackMap;
    }
}
