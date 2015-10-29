package com.olymtech.nebula.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("helloMvcController")
@RequestMapping("/")
public class HelloMvcController {	

	
	@Resource
	HttpServletRequest request;
	
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String helloMvc(){
		return "index";
	}
}

