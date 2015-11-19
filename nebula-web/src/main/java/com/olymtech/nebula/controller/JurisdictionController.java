package com.olymtech.nebula.controller;

import com.olymtech.nebula.entity.AclRole;
import com.olymtech.nebula.service.IAclRoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by liwenji on 2015/11/17.
 */
@Controller("JurisdictionController")
@RequestMapping("/")
public class JurisdictionController extends BaseController {

    @Resource
    private IAclRoleService aclRoleService;

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

    @RequestMapping(value="/editRole.html",method= {RequestMethod.POST,RequestMethod.GET})
    public String editRole(Model model,Integer id){
//        AclRole aclRole=aclRoleService.
//                model.addAttribute("productTrees");
        return "event/publishEvent";
    }

}
