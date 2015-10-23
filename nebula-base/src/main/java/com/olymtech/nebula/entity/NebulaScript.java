/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

/**
 * Created by Gavin on 2015-10-23 14:57.
 */
public class NebulaScript {

    private String scriptName;

    private String scriptPath;

    private String scriptType;

    private String scriptContext;

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName == null ? null : scriptName.trim();
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath == null ? null : scriptPath.trim();
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType == null ? null : scriptType.trim();
    }

    public String getScriptContext() {
        return scriptContext;
    }

    public void setScriptContext(String scriptContext) {
        this.scriptContext = scriptContext == null ? null : scriptContext.trim();
    }
}
