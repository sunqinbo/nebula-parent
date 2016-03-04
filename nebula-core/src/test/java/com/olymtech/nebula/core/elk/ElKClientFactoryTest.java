package com.olymtech.nebula.core.elk;

import com.olymtech.nebula.common.utils.DateUtils;
import com.olymtech.nebula.common.utils.EsUtils;
import com.olymtech.nebula.core.elk.core.ElKClientFactory;
import com.olymtech.nebula.entity.ElkSearchData;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.Date;

/**
 * Created by Gavin on 2016-01-28 16:56.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class ElKClientFactoryTest {

    @Autowired
    private IElKClientService elKClientService;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSearch() throws Exception {
        String fromDate = "2016-02-02 00:34:36";
        String toDate = "2016-02-02 23:34:36";

        ElkSearchData elkSearchData = new ElkSearchData("stage_pm_web01", DateUtils.strToDate(fromDate),DateUtils.strToDate(toDate),1,10);
        SearchResponse searchResponse = elKClientService.search(elkSearchData,"stage");
        long total = searchResponse.getHits().getTotalHits();
        System.out.println(total);
        for(SearchHit hit:searchResponse.getHits()){
            String host = (String)hit.getSource().get("host");
            String message = (String)hit.getSource().get("message");
            String file = (String)hit.getSource().get("file");
            String timestamp = (String)hit.getSource().get("@timestamp");
            String type = (String)hit.getSource().get("type");
            String id = hit.getId();
            String index = hit.getIndex();

            System.out.println(host+":::::"+message);
        }

    }

    @Test
    public void testCount() throws Exception {
        String dateString = "2016-01-31 23:34:36";
        Date date = DateUtils.strToDate(dateString);
        System.out.println(date);

        String specificDateString = EsUtils.specificDateToString(date);
        System.out.println(specificDateString);
        Date specificDate = EsUtils.stringToSpecificDate(specificDateString);
        System.out.println(specificDate);


    }


}