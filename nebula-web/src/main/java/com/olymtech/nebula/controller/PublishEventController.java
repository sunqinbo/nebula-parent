package com.olymtech.nebula.controller;

import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.service.IPublishEventService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by WYQ on 2015/11/3.
 */
@Controller("publishEventController")
@RequestMapping("/")
public class PublishEventController extends BaseController{

    @Resource
    IPublishEventService publishEventService;


    @Resource
    HttpServletRequest request;

    @RequestMapping(value="/publishEvent.htm",method= {RequestMethod.POST,RequestMethod.GET})
     public String publishEvent(){
        return "publishEvent";
    }

    @RequestMapping(value="/publishList.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String publishList(){
        return "publishList";
    }

    @RequestMapping(value="/publishProcess.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String publishProcess(){
        return "publishProcess";
    }

    @RequestMapping(value="/createPublishEvent.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Object createPublishEvent(NebulaPublishEvent nebulaPublishEvent){
        if (publishEventService.createPublishEvent(nebulaPublishEvent)<1) {
            return returnCallback("Error", "create failure");
        }
        return returnCallback("Success","create Success");
    }



}
