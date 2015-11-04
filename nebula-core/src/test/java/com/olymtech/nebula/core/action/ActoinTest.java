/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action;

import com.olymtech.nebula.entity.NebulaPublishEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taoshanchang 15/11/4
 */
public class ActoinTest {

    @Before
    public void init() throws Exception {
    }

    @Test
    public void actionTest(){
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(new Action1("dir")).addAction(new Action2("svn")).addAction(new Action3("salt"));

        Action1 action1 = new Action1("111");
        Action2 action2 = new Action2("222");
        Action3 action3 = new Action3("333");

        List<Action> list = new ArrayList<Action>();
        list.add(action1);
        list.add(action2);
        list.add(action3);

        chain.addActions(list);
        //创建拦截器
        Dispatcher dispatcher = new Dispatcher(chain);

        try {
            //执行拦截器
            dispatcher.doDispatch(new NebulaPublishEvent());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
