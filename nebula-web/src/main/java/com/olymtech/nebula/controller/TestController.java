package com.olymtech.nebula.controller;

import com.olymtech.nebula.core.elk.IElKClientService;
import com.olymtech.nebula.entity.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class TestController extends BaseController{

	@Autowired
	private IElKClientService elKClientService;

	@RequestMapping(value = "/testaaa", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Callback testaaa() {
		return returnCallback("Success", "test成功");
	}

}

