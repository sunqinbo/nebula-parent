package com.olymtech.nebula.entity;

/**
 * Created by liwenji on 2015/11/19.
 */
public class zNode extends BaseDO{
    private Integer pid;
    private String name;
    private boolean open;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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
