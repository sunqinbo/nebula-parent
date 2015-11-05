package com.olymtech.nebula.controller;
import com.olymtech.nebula.entity.JsTreeData;
import com.olymtech.nebula.entity.JsTreeDataState;
import com.olymtech.nebula.file.analyze.IFileAnalyzeService;
import com.olymtech.nebula.service.IFileReadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller("helloMvcController")
@RequestMapping("/")
public class HelloMvcController extends BaseController{

	
	@Resource
	HttpServletRequest request;

	@Resource
	IFileReadService fileReadService;

	@Resource
	IFileAnalyzeService fileAnalyzeService;
	
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String helloMvc(){
		return "index";
	}

	@RequestMapping(value="/user.html",method=RequestMethod.GET)
	public String UserInfo(){
		return "user";
	}

	@RequestMapping(value="/icons.html",method=RequestMethod.GET)
	public String icons(){
		return "icons";
	}

	@RequestMapping(value="/maps.html",method=RequestMethod.GET)
	public String maps(){
		return "maps";
	}
	@RequestMapping(value="/notifications.html",method=RequestMethod.GET)
	public String notifications(){
		return "notifications";
	}
	@RequestMapping(value="/table.html",method=RequestMethod.GET)
	public String table(){
		return "table";
	}

	@RequestMapping(value="/typography.html",method=RequestMethod.GET)
	public String typography(){
		return "typography";
	}

	@RequestMapping(value="/fileEdit.html",method=RequestMethod.GET)
	public String fileEdit(){
		return "fileEdit";
	}

	@RequestMapping(value="/filePath",method=RequestMethod.POST)
	@ResponseBody
	public Object fileContent(String path) throws IOException {
//		path+="成功了hahahaha！";
		List<String> filecontent=fileReadService.ReadFile(path);
		return returnCallback("success",filecontent);
	}

	@RequestMapping(value="/fileSave",method=RequestMethod.POST)
	@ResponseBody
	public Object fileSaveContent(String path,String filecontent) throws IOException {
		System.out.println(filecontent);
		fileReadService.SaveFile(path,filecontent);
		return returnCallback("success",filecontent);
	}

	@RequestMapping(value = {"etcList.htm"}, method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object productList(HttpServletRequest request) {
		String idString = request.getParameter("id");
		String id = "F:\\121";
		Map<String,Boolean> Srcmap=fileAnalyzeService.getDirMapByDirPath(id);
		/** 初始化页面 加载，有root信息，返回 jsTreeDataRoots */
		if (idString.equals("#")) {
			/** 根节点为0 */
//			String path="F:\\121";
			List<JsTreeData> jsTreeDataRoots = new ArrayList<JsTreeData>();
			JsTreeData jsTreeData=new JsTreeData();
			jsTreeData.setId(id);
			jsTreeData.setParent("/");
			jsTreeData.setText("Root");
			JsTreeDataState jsTreeDataState=new JsTreeDataState(true, false, false);
			jsTreeData.setState(jsTreeDataState);
			/** 设置多个 根节点 */
//			for(ArsenalProductTree arsenalProductTree:arsenalProductTrees){
//				JsTreeDataRoot jsTreeDataRoot = arsenalProductTreeService.setJsTreeDataRootByTreeId(arsenalProductTree.getId());
//				jsTreeDataRoots.add(jsTreeDataRoot);
//			}
			if(Srcmap.size()>0){
				jsTreeData.setChildren(true);
			}
			else
				jsTreeData.setChildren(false);
			return jsTreeDataRoots;
		}

		/** 后续点击节点 加载，只需要子节点列表，返回 jsTreeDatas */
//		if(!idString.equals("#") && StringUtils.isNotEmpty(idString)){
//			id = Integer.parseInt(idString);
//		}
		List<JsTreeData> jsTreeDatas =new ArrayList<>();
		Set<String> keys=Srcmap.keySet();
		Iterator<String> it=keys.iterator();
		while(it.hasNext()) {
			String path=it.next();
			JsTreeData jsTreeData=new JsTreeData();
			jsTreeData.setId(path);
			jsTreeData.setParent("id");
			jsTreeData.setText("path");
			JsTreeDataState jsTreeDataState=new JsTreeDataState(false, false, false);
			jsTreeData.setState(jsTreeDataState);
			jsTreeDatas.add(jsTreeData);
		}

		return jsTreeDatas;
	}
}

