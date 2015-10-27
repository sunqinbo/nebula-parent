package com.olym.nebula.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hello")
public class HelloMvcController {	

	
	@Resource
	HttpServletRequest request;
	
	
	@RequestMapping(value="/mvc",method=RequestMethod.GET)
	//响应host:8080/hello/mvc
	public String helloMvc(){
		return "index";
	}
}

