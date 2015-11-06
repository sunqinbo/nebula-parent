/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

/**
 * Created by Gavin on 2015-09-25 17:01.
 */
public class JsTreeDataRoot {
    private String id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
