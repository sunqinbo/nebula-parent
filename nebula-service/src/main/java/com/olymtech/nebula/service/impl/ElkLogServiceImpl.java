/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.core.elk.ElasticsearchFactory;
import com.olymtech.nebula.entity.ElkLogData;
import com.olymtech.nebula.entity.ElkSearchData;
import com.olymtech.nebula.service.IElkLogService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin on 2016-02-02 12:38.
 */
@Service
public class ElkLogServiceImpl implements IElkLogService {

    private Logger logger       = LoggerFactory.getLogger(this.getClass());

    @Override
    public PageInfo search(ElkSearchData elkSearchData){
        ElasticsearchFactory ef = new ElasticsearchFactory();
        SearchResponse searchResponse = ef.search(elkSearchData);
        long total = searchResponse.getHits().getTotalHits();
        elkSearchData.setTotal(total);

        List<ElkLogData> elkLogDatas = new ArrayList<>();
        for(SearchHit hit:searchResponse.getHits()){
            String host = (String)hit.getSource().get("host");
            String message = (String)hit.getSource().get("message");
            String file = (String)hit.getSource().get("file");
            String timestamp = (String)hit.getSource().get("@timestamp");
            String type = (String)hit.getSource().get("type");
            String id = hit.getId();
            String index = hit.getIndex();
            ElkLogData elkLogData = new ElkLogData(message,timestamp,file,host,type,index,id);
            elkLogDatas.add(elkLogData);
        }

        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(elkLogDatas);
        pageInfo.setTotal(total);
        pageInfo.setPageNum(elkSearchData.getPageNum());
        pageInfo.setPageSize(elkSearchData.getPageSize());
        pageInfo.setPages(elkSearchData.getPages());
        return pageInfo;
    }

    @Override
    public Integer count(ElkSearchData elkSearchData){
        ElasticsearchFactory ef = new ElasticsearchFactory();
        SearchResponse searchResponse = ef.search(elkSearchData);
        long total = searchResponse.getHits().getTotalHits();
        int totalint = new Long(total).intValue();
        return totalint;
    }

}
