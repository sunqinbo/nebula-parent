/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.core.elk;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gavin on 2016-01-28 15:45.
 */
public class ElasticsearchFactory {

    private static Logger logger = LoggerFactory.getLogger(ElasticsearchFactory.class);

    public TransportClient initClient(){
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "StageElkCluster")
                .build();

        TransportClient client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("121.40.51.107", 9300));
        return client;
    }

    public SearchResponse search(TransportClient client,String host,String keyWord){
        FilterBuilder boolFilter =  FilterBuilders.boolFilter()
                .must(FilterBuilders.rangeFilter("@timestamp").from(EsUtils.oneDayAgoFrom()).to(EsUtils.nowDate()).includeLower(true).includeUpper(false))
                .must(FilterBuilders.termFilter("host", host));
//                .must(FilterBuilders.termFilter("message", keyWord.toLowerCase()));

        SearchResponse searchResponse = client.prepareSearch("logstash-2016.01*")
                .setPostFilter(boolFilter).setFrom(1).setSize(10).execute().actionGet();
        return searchResponse;
    }

    public Long count(TransportClient client){
        SearchResponse searchResponse = client.prepareSearch("logstash-2016.01.26")
                .setFrom(1).setSize(10).execute().actionGet();
        long totalHits = searchResponse.getHits().totalHits();
        return totalHits;
    }


}
