/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

/**
 * Created by Gavin on 2015-09-26 08:04.
 */
public class JsTreeDataState {
    private Boolean opened = false;
    private Boolean disabled = false;
    private Boolean selected = false;

    public JsTreeDataState(){
        super();
    }

    public JsTreeDataState(Boolean opened,Boolean disabled,Boolean selected){
        this.opened = opened;
        this.disabled = disabled;
        this.selected = selected;
    }

    public Boolean getOpened() {
        return opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
