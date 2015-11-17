package com.olymtech.nebula.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liwenji on 2015/11/17.
 */
@Controller("JurisdictionController")
@RequestMapping("/")
public class JurisdictionController extends BaseController {

    @RequestMapping(value="/jurisdiction.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String jurisdiction (){
        return "event/jurisdiction";
    }

    @RequestMapping(value="/createPermission.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String createPermission (){
        return "event/createPermission";
    }

    @RequestMapping(value="/roleList.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String roleList (){
        return "event/roleList";
    }

    @RequestMapping(value="/createRole.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String createRole (){
        return "event/createRole";
    }
}
