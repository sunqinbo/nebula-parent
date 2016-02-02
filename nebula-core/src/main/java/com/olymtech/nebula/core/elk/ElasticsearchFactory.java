/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.core.elk;

import com.olymtech.nebula.common.utils.DateUtils;
import com.olymtech.nebula.common.utils.EsUtils;
import com.olymtech.nebula.entity.ElkSearchData;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Gavin on 2016-01-28 15:45.
 */
public class ElasticsearchFactory {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchFactory.class);

    private TransportClient client = initClient();

    public TransportClient initClient(){
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "StageElkCluster")
                .build();

        TransportClient client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("121.40.51.107", 9300));
        return client;
    }

    public SearchResponse search(ElkSearchData elkSearchData){
        FilterBuilder boolFilter = null;
        if(elkSearchData.getKeyWord() == null){
            boolFilter =  FilterBuilders.boolFilter()
                    .must(FilterBuilders.rangeFilter("@timestamp").from(elkSearchData.getFromDate()).to(elkSearchData.getToDate()).includeLower(true).includeUpper(false))
                    .must(FilterBuilders.termFilter("host", elkSearchData.getHost()));
        }else{
            boolFilter =  FilterBuilders.boolFilter()
                    .must(FilterBuilders.rangeFilter("@timestamp").from(elkSearchData.getFromDate()).to(elkSearchData.getToDate()).includeLower(true).includeUpper(false))
                    .must(FilterBuilders.termFilter("host", elkSearchData.getHost()))
                    .must(FilterBuilders.termFilter("message", elkSearchData.getKeyWord().toLowerCase()));
        }

        //"logstash-2016.01*"
        SearchResponse searchResponse = client.prepareSearch(getElkIndex(elkSearchData))
                .setPostFilter(boolFilter).setFrom(elkSearchData.getPageFrom()).setSize(elkSearchData.getPageSize()).execute().actionGet();
        return searchResponse;
    }

    public Long count(ElkSearchData elkSearchData){
        SearchResponse searchResponse = client.prepareSearch(getElkIndex(elkSearchData))
                .setFrom(elkSearchData.getPageFrom()).setSize(elkSearchData.getPageSize()).execute().actionGet();
        long totalHits = searchResponse.getHits().totalHits();
        return totalHits;
    }

    private String getElkIndex(ElkSearchData elkSearchData){
        String indexPrefix = "logstash-";
        Date fromDate = EsUtils.stringToSpecificDate(elkSearchData.getFromDate());
        String indexDate = DateUtils.getElkSimpleIndex(fromDate);
        return indexPrefix + indexDate;
    }


}
