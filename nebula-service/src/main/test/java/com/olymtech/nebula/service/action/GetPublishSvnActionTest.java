package com.olymtech.nebula.service.action;

import com.olymtech.nebula.core.action.ActionChain;
import com.olymtech.nebula.core.action.Dispatcher;
import com.olymtech.nebula.entity.NebulaPublishApp;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Gavin on 2015-11-05 20:16.
 */
public class GetPublishSvnActionTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testDoAction() throws Exception {
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(new GetPublishSvnAction());
        chain.addAction(new GetSrcSvnAction());

        Dispatcher dispatcher = new Dispatcher(chain,null,null);

        try {
            NebulaPublishEvent event = new NebulaPublishEvent();
            event.setPublishEnv("test");
            event.setPublishProductKey("test.product.2015111.22222");
            event.setPublishSvn("svn://172.16.137.150/war");
            event.setProductSrcSvn("svn://172.16.137.150/yjt2014");
            dispatcher.doDispatch(event);
            System.out.println("success");

        }catch(Exception e){
            e.printStackTrace();
        }


    }

}