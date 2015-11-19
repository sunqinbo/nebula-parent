package com.olymtech.nebula.entity;

/**
 * Created by liwenji on 2015/11/19.
 */
public class zNode extends BaseDO{
    private Integer pId;
    private String name;
    private boolean open;

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
