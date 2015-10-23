package com.olymtech.nebula.entity;

public class NebulaScriptHistory extends BaseDO{

    private Integer scriptId;

    private String scriptNameH;

    private String scriptPathH;

    private String scriptTypeH;

    private String scriptContextH;

    public Integer getScriptId() {
        return scriptId;
    }

    public void setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
    }

    public String getScriptNameH() {
        return scriptNameH;
    }

    public void setScriptNameH(String scriptNameH) {
        this.scriptNameH = scriptNameH == null ? null : scriptNameH.trim();
    }

    public String getScriptPathH() {
        return scriptPathH;
    }

    public void setScriptPathH(String scriptPathH) {
        this.scriptPathH = scriptPathH == null ? null : scriptPathH.trim();
    }

    public String getScriptTypeH() {
        return scriptTypeH;
    }

    public void setScriptTypeH(String scriptTypeH) {
        this.scriptTypeH = scriptTypeH == null ? null : scriptTypeH.trim();
    }

    public String getScriptContextH() {
        return scriptContextH;
    }

    public void setScriptContextH(String scriptContextH) {
        this.scriptContextH = scriptContextH == null ? null : scriptContextH.trim();
    }
}