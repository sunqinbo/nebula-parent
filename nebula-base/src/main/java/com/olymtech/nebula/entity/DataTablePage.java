package com.olymtech.nebula.entity;

import java.util.Map;

/**
 * Created by liwenji on 2015/11/16.
 */
public class DataTablePage {
    private int pageSize;
    private int pageNum;
    private Map<String,Object> map;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
