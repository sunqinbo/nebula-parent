package com.olymtech.nebula.controller;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.common.constants.Constants;
import com.olymtech.nebula.entity.ProductTree;
import com.olymtech.nebula.service.IAnalyzeArsenalApiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController extends BaseController{

	@Resource
	IAnalyzeArsenalApiService analyzeArsenalApiService;

	@RequestMapping(value={"/", "index", "index.htm"},method=RequestMethod.GET)
	public String index(Model model){
//		NebulaUserInfo user = (NebulaUserInfo)request.getAttribute(Constants.CURRENT_USER);
		List<ProductTree> productTrees = analyzeArsenalApiService.getProductTreeListByPid(2);
		model.addAttribute("productTrees", productTrees);
		return "event/publishList";
	}

}

