/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.entity;

import com.olymtech.nebula.common.utils.EsUtils;

import java.util.Date;

/**
 * Created by Gavin on 2016-02-02 09:49.
 */
public class ElkSearchData {

    private String host;

    private String keyWord;

    /** 第几页 */
    private Integer pageNum;

    /** 每页条数 */
    private Integer pageSize;

    /** 从0开始，第0条 */
    private Integer pageFrom;

    /** 一共多少页 */
    private Integer pages;

    private long total;

    /**
     * "yyyy-MM-dd'T'HH:mm:ss.000'Z'"
     * 这里的时间格式
     */
    private String fromDate;

    /**
     * "yyyy-MM-dd'T'HH:mm:ss.000'Z'"
     * 这里的时间格式
     */
    private String toDate;

    /**
     * yyyy-MM-dd HH:mm:ss
     * 为了接收前端参数
     */
    private String toDateString;

    public ElkSearchData(){
        super();
    }

    public ElkSearchData(String host,
                         Date fromDate,
                         Date toDate,
                         Integer pageNum,
                         Integer pageSize){
        super();
        this.host = host;
        this.fromDate = EsUtils.specificDateToString(fromDate);
        this.toDate = EsUtils.specificDateToString(toDate);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public ElkSearchData(String host,
                         String keyWord,
                         Date fromDate,
                         Date toDate,
                         Integer pageNum,
                         Integer pageSize){
        super();
        this.host = host;
        this.keyWord = keyWord;
        this.fromDate = EsUtils.specificDateToString(fromDate);
        this.toDate = EsUtils.specificDateToString(toDate);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPages() {
        //向上取整
        pages = (int)Math.ceil(total/pageSize);
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = EsUtils.specificDateToString(fromDate);
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = EsUtils.specificDateToString(toDate);
    }

    public Integer getPageFrom() {
        pageFrom = pageSize*(pageNum-1);
        return pageFrom;
    }

    public void setPageFrom(Integer pageFrom) {
        this.pageFrom = pageFrom;
    }

    public String getToDateString() {
        return toDateString;
    }

    public void setToDateString(String toDateString) {
        this.toDateString = toDateString;
    }
}
