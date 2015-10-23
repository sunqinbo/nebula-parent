package com.olymtech.nebula.entity;

public class NebulaScriptGroup extends BaseDO{

    private String scriptGroupName;

    public String getScriptGroupName() {
        return scriptGroupName;
    }

    public void setScriptGroupName(String scriptGroupName) {
        this.scriptGroupName = scriptGroupName == null ? null : scriptGroupName.trim();
    }
}