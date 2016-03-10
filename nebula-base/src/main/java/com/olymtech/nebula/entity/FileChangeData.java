/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

/**
 * Created by Gavin on 2016-03-10 14:08.
 */
public class FileChangeData {

    private String change;

    private String filename;

    private String time;

    public FileChangeData(){

    }

    public FileChangeData(String change,
                          String filename,
                          String time){
        this.change = change;
        this.filename = filename;
        this.time = time;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
