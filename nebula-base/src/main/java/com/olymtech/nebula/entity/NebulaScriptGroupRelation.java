package com.olymtech.nebula.entity;

public class NebulaScriptGroupRelation extends BaseDO{

    private Integer scriptGroupId;

    private Integer scriptId;

    private String scriptSeq;

    public Integer getScriptGroupId() {
        return scriptGroupId;
    }

    public void setScriptGroupId(Integer scriptGroupId) {
        this.scriptGroupId = scriptGroupId;
    }

    public Integer getScriptId() {
        return scriptId;
    }

    public void setScriptId(Integer scriptId) {
        this.scriptId = scriptId;
    }

    public String getScriptSeq() {
        return scriptSeq;
    }

    public void setScriptSeq(String scriptSeq) {
        this.scriptSeq = scriptSeq == null ? null : scriptSeq.trim();
    }
}