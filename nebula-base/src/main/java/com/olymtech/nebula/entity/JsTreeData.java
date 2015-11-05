/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

/**
 * Created by Gavin on 2015-09-25 17:01.
 */
public class JsTreeData {
    private Object id;
    private String parent;
    private String text;
    private Object children;
    private JsTreeDataState state;

    public JsTreeDataState getState() {
        return state;
    }

    public void setState(JsTreeDataState state) {
        this.state = state;
    }

    public Object getChildren() {
        return children;
    }

    public void setChildren(Object children) {
        this.children = children;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
