/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.service.impl;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.core.elk.IElKClientService;
import com.olymtech.nebula.core.elk.core.ElKClientFactory;
import com.olymtech.nebula.entity.ElkLogData;
import com.olymtech.nebula.entity.ElkSearchData;
import com.olymtech.nebula.service.IElkLogService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin on 2016-02-02 12:38.
 */
@Service
public class ElkLogServiceImpl implements IElkLogService {

    private Logger logger       = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IElKClientService elKClientService;

    @Override
    public PageInfo search(ElkSearchData elkSearchData,String publishEnv){
        PageInfo pageInfo = new PageInfo();

        try{
            SearchResponse searchResponse = elKClientService.search(elkSearchData,publishEnv);
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


            pageInfo.setList(elkLogDatas);
            pageInfo.setTotal(total);
            pageInfo.setPageNum(elkSearchData.getPageNum());
            pageInfo.setPageSize(elkSearchData.getPageSize());
            pageInfo.setPages(elkSearchData.getPages());
        }catch (NoNodeAvailableException e){
            logger.error("None of the configured nodes are available: []:",elkSearchData.getHost());
        }catch (Exception e){
            logger.error("search error:",e);
        }
        return pageInfo;
    }

    @Override
    public Integer count(ElkSearchData elkSearchData,String publishEnv){
        int totalint = -1;
        try{
            SearchResponse searchResponse = elKClientService.search(elkSearchData,publishEnv);
            long total = searchResponse.getHits().getTotalHits();
            totalint = new Long(total).intValue();
        }catch (NoNodeAvailableException e){
            logger.error("None of the configured nodes are available: []:",elkSearchData.getHost());
        }catch (Exception e){
            logger.error("count error:",e);
        }
        return totalint;
    }

}
