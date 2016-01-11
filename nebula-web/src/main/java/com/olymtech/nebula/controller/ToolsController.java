package com.olymtech.nebula.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liwenji on 2016/1/11.
 */
@Controller
@RequestMapping("/tool")
public class ToolsController extends BaseController {
    @RequestMapping(value="/freshCDN.htm",method = {RequestMethod.POST, RequestMethod.GET})
    public String freshCDN(){
        return "event/freshCDN";
    }
}
