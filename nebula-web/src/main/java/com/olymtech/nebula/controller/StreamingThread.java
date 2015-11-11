/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.controller;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author taoshanchang 15/11/10
 */
public class StreamingThread extends Thread {
    private HttpServletResponse response = null;

    public StreamingThread(HttpServletResponse response){
        this.response = response;
    }

    @Override
    public void run() {
        try{
            String message = "your hava a new message";
            PrintWriter writer = response.getWriter();
            for(int i = 0 ,max = message.length(); i < max ; i++) {
                writer.print(message.substring(i,i+1));
                writer.flush();
                sleep(1000);
            }
            writer.close();
        }catch (Exception e) {
            // TODO: handle exception
        }

    }
}