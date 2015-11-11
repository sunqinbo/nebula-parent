/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author taoshanchang 15/11/10
 */

@Controller
public class TestController {

    @RequestMapping(value = "/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {

        //System.out.println("-----CometAction.streaming.start");
        //StreamingThread st = new StreamingThread(response);
        //st.run();
        //System.out.println("-----CometAction.streaming.end");
        return "test";
    }

    @RequestMapping(value = "/test1")
    public void test1(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("-----CometAction.streaming.start");
        StreamingThread st = new StreamingThread(response);
        st.run();
        System.out.println("-----CometAction.streaming.end");
    }


}
