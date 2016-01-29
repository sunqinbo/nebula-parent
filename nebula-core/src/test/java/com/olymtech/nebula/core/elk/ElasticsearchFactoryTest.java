package com.olymtech.nebula.core.elk;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2016-01-28 16:56.
 */
public class ElasticsearchFactoryTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSearch() throws Exception {
        ElasticsearchFactory ef = new ElasticsearchFactory();
        TransportClient client = ef.initClient();
        SearchResponse searchResponse = ef.search(client,"stage_exam", "*");
        long total = searchResponse.getHits().getTotalHits();
        System.out.println(total);
        for(SearchHit hit:searchResponse.getHits()){
            String host = (String)hit.getSource().get("host");
            String message = (String)hit.getSource().get("message");
            System.out.println(host+":::::"+message);
        }

    }

    @Test
    public void testCount() throws Exception {

    }
}