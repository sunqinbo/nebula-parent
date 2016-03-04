/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.core.elk;

import com.olymtech.nebula.common.utils.DateUtils;
import com.olymtech.nebula.common.utils.EsUtils;
import com.olymtech.nebula.core.elk.core.ElKClientFactory;
import com.olymtech.nebula.entity.ElkSearchData;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Gavin on 2016-02-14 14:48.
 */
@Service
public class ElKClientServiceImpl implements IElKClientService {

    private static Logger logger = LoggerFactory.getLogger(ElKClientServiceImpl.class);

    @Value("${elk.stage.server.ip}")
    private String elkStageServerIp;

    @Value("${elk.product.server.ip}")
    private String elkProductServerIp;

    public TransportClient getElkClient(String publishEnv){
        if(publishEnv.equals("product")){
            return ElKClientFactory.getProductClient(elkProductServerIp);
        }else {
            return ElKClientFactory.getStageClient(elkStageServerIp);
        }
    }

    @Override
    public SearchResponse search(ElkSearchData elkSearchData,String publishEnv){
        FilterBuilder boolFilter = null;
        if(!StringUtils.isNotEmpty(elkSearchData.getKeyWord())){
            boolFilter =  FilterBuilders.boolFilter()
                    .must(FilterBuilders.rangeFilter("@timestamp").from(elkSearchData.getFromDate()).to(elkSearchData.getToDate()).includeLower(true).includeUpper(false))
                    .must(FilterBuilders.termFilter("type", "tomcat"))
                    .must(FilterBuilders.termFilter("host", elkSearchData.getHost()));
        }else{
            boolFilter =  FilterBuilders.boolFilter()
                    .must(FilterBuilders.rangeFilter("@timestamp").from(elkSearchData.getFromDate()).to(elkSearchData.getToDate()).includeLower(true).includeUpper(false))
                    .must(FilterBuilders.termFilter("type", "tomcat"))
                    .must(FilterBuilders.termFilter("host", elkSearchData.getHost()))
                    .must(FilterBuilders.termFilter("message", elkSearchData.getKeyWord().toLowerCase()));
        }

        //"logstash-2016.01*"

        SearchResponse searchResponse = getElkClient(publishEnv).prepareSearch(getElkIndex(elkSearchData))
                .setPostFilter(boolFilter)
                .setFrom(elkSearchData.getPageFrom()).setSize(elkSearchData.getPageSize())
                .addSort("@timestamp", SortOrder.DESC).execute().actionGet();
        return searchResponse;
    }

    @Override
    public Long count(ElkSearchData elkSearchData,String publishEnv){
        SearchResponse searchResponse = getElkClient(publishEnv).prepareSearch(getElkIndex(elkSearchData))
                .setFrom(elkSearchData.getPageFrom()).setSize(elkSearchData.getPageSize()).execute().actionGet();
        long totalHits = searchResponse.getHits().totalHits();
        return totalHits;
    }

    private String getElkIndex(ElkSearchData elkSearchData){
        String indexPrefix = "logstash-";
        Date fromDate = EsUtils.stringToSpecificDate(elkSearchData.getFromDate());
        Date toDate = EsUtils.stringToSpecificDate(elkSearchData.getToDate());

//        String fromDateYear = DateUtils.getYear(fromDate);
        String fromDateMonth = DateUtils.getMonth(fromDate);
        String fromDateDay = DateUtils.getDay(fromDate);

//        String toDateYear = DateUtils.getYear(toDate);
        String toDateMonth = DateUtils.getMonth(toDate);
        String toDateDay = DateUtils.getDay(toDate);

        if(fromDateMonth.equals(toDateMonth) && fromDateDay.equals(toDateDay) ){
            String indexDate = DateUtils.getElkSimpleIndex(fromDate);
            return indexPrefix + indexDate;
        }else if(fromDateMonth.equals(toDateMonth) && !fromDateDay.equals(toDateDay)){
            String indexDate = DateUtils.getElkSimpleIndexFuzzyDay(fromDate);
            return indexPrefix + indexDate;
        }else if(!fromDateMonth.equals(toDateMonth)){
            String indexDate = DateUtils.getElkSimpleIndexFuzzyMonth(fromDate);
            return indexPrefix + indexDate;
        }else{
            String indexDate = "*";
            return indexPrefix + indexDate;
        }
    }

}
