/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

/**
 * Created by Gavin on 2016-02-02 12:39.
 */
public class ElkLogData {

    private String message;

    private String timestamp;

    private String file;

    private String host;

    private String type;

    private String index;

    private String id;

    public ElkLogData(){
        super();
    }

    public ElkLogData(String message,
                      String timestamp,
                      String file,
                      String host,
                      String type,
                      String index,
                      String id){
        super();
        this.message = message;
        this.timestamp = timestamp;
        this.file = file;
        this.host = host;
        this.type = type;
        this.index = index;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
