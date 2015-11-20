/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.controller;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

/**
 * @author taoshanchang 15/11/10
 */

@Controller
public class TestController {

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        return "test";
    }

    @RequestMapping(value = "/test1")
    public void test1(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("-----CometAction.streaming.start");
        StreamingThread st = new StreamingThread(response);
        st.run();
        System.out.println("-----CometAction.streaming.end");
    }

    @RequestMapping(value = "/test2")
    public void test2(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("xxxx");
        Event event = Event.createDataEvent("/guoguo/myevent1");


        while (true){
            try {
                Thread.sleep(1000);
                event.setField("key1", new Random().nextInt()+"xxxx");
                Dispatcher.getInstance().broadcast(event); // 向所有和myevent1名称匹配的事件推送
                System.out.println("wa success....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



    }




}
