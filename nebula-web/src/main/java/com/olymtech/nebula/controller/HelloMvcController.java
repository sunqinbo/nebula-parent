package com.olymtech.nebula.controller;
import com.olymtech.nebula.entity.JsTreeData;
import com.olymtech.nebula.entity.JsTreeDataRoot;
import com.olymtech.nebula.entity.JsTreeDataState;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.file.analyze.IFileAnalyzeService;
import com.olymtech.nebula.service.IFileReadService;
import com.olymtech.nebula.web.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
		NebulaUserInfo user = (NebulaUserInfo)request.getAttribute(Constants.CURRENT_USER);
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
		fileReadService.SaveFile(path,filecontent);
		return returnCallback("success",filecontent);
	}

	@RequestMapping(value = {"/etcList.htm"}, method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public Object productList(HttpServletRequest request) throws UnsupportedEncodingException {
		String idString = request.getParameter("id");
		idString = new String(idString.getBytes("ISO-8859-1"),"utf-8");
		/** 初始化页面 加载，有root信息，返回 jsTreeDataRoots */
		if (idString.equals("#")) {
			/** 根节点为0 */
			idString="F:\\121";
			Map<String,Boolean> Srcmap=fileAnalyzeService.getDirMapByDirPath(idString);
			List<JsTreeDataRoot> jsTreeDataRoots = new ArrayList<JsTreeDataRoot>();
			JsTreeDataRoot jsTreeDataRoot=new JsTreeDataRoot();
			jsTreeDataRoot.setId(idString);
			jsTreeDataRoot.setText("Root");
//			jsTreeDataRoot.setData(false);
			JsTreeDataState jsTreeDataState=new JsTreeDataState(false, false, false);
			jsTreeDataRoot.setState(jsTreeDataState);


			/** 设置子节点列表*/

			List<JsTreeData> Childrens =new ArrayList<>();
			Set<String> keys=Srcmap.keySet();
			Iterator<String> it=keys.iterator();
			while(it.hasNext()) {
				String path = it.next();
				JsTreeData Children = new JsTreeData();
				if(Srcmap.get(path)){
//					Children.setData(false);
				}
				else
//					Children.setData(true);
				Children.setId(idString+"\\"+path);
				Children.setParent(idString);
				Children.setText(path);
				JsTreeDataState childtreeDataState = new JsTreeDataState(false, false, false);
				Children.setState(jsTreeDataState);
				if(Srcmap.get(path)){
					Map<String,Boolean> hasChild=fileAnalyzeService.getDirMapByDirPath((String)Children.getId());
					if(hasChild.size()>0){
						Children.setChildren(true);
					}
					else
						Children.setChildren(false);
				}
				else
					Children.setChildren(false);
				Childrens.add(Children);
			}

			if(Srcmap.size()>0){
				jsTreeDataRoot.setChildren(Childrens);
			}
			else
				jsTreeDataRoot.setChildren(Childrens);
			jsTreeDataRoots.add(jsTreeDataRoot);
			return jsTreeDataRoots;
		}

		/** 后续点击节点 加载，只需要子节点列表，返回 jsTreeDatas */
//		if(!idString.equals("#") && StringUtils.isNotEmpty(idString)){
//			id = Integer.parseInt(idString);
//		}
		Map<String,Boolean> childSrcmap=fileAnalyzeService.getDirMapByDirPath(idString);
		List<JsTreeData> jsTreeDatas =new ArrayList<>();
		Set<String> ckey=childSrcmap.keySet();
		Iterator<String> cit=ckey.iterator();
		while(cit.hasNext()) {
			String path=cit.next();
			JsTreeData jsTreeData=new JsTreeData();
			jsTreeData.setId(idString+"\\"+path);
			jsTreeData.setParent(idString);
			jsTreeData.setText(path);
			JsTreeDataState cjsTreeDataState=new JsTreeDataState(false, false, false);
			jsTreeData.setState(cjsTreeDataState);
			if(childSrcmap.get(path)){
//				jsTreeData.setData(false);
				Map<String,Boolean> hasChild=fileAnalyzeService.getDirMapByDirPath((String)jsTreeData.getId());
				if(hasChild.size()>0){
					jsTreeData.setChildren(true);
				}
				else
					jsTreeData.setChildren(false);
			}
			else {
//				jsTreeData.setData(true);
				jsTreeData.setChildren(false);
			}
			jsTreeDatas.add(jsTreeData);
		}

		return jsTreeDatas;
	}
}

