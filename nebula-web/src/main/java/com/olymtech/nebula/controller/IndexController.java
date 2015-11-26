package com.olymtech.nebula.controller;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.common.constants.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{

	@RequestMapping(value="/",method=RequestMethod.GET)
	public String helloMvc(){
		NebulaUserInfo user = (NebulaUserInfo)request.getAttribute(Constants.CURRENT_USER);
		return "event/publishList";
	}

}

