package com.olymtech.nebula.controller;

import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.service.IFileAnalyzeService;
import com.olymtech.nebula.service.IFileReadService;
import com.olymtech.nebula.service.IPublishEventService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by liwenji on 2015/11/11.
 */
@Controller
@RequestMapping("/etc_edit")
public class FileController extends BaseController {
    @Resource
    HttpServletRequest request;
    @Resource
    IFileReadService fileReadService;
    @Resource
    IFileAnalyzeService fileAnalyzeService;
    @Resource
    IPublishEventService publishEventService;

    @Value("${master_deploy_dir}")
    private String MasterDeployDir;

    @RequestMapping(value="/fileEdit.htm",method= RequestMethod.GET)
    public String fileEdit(Model model){
        String idString = request.getParameter("id");
        if(StringUtils.isNotEmpty(idString)){
            model.addAttribute("eventId",Integer.parseInt(idString));
        }
        return "event/fileEdit";
    }

    @RequestMapping(value="/filePath",method=RequestMethod.POST)
    @ResponseBody
    public Object fileContent(String path) throws IOException {
        List<String> filecontent=fileReadService.ReadFile(path);
        return returnCallback("success",filecontent);
    }

    @RequestMapping(value="/fileSave",method=RequestMethod.POST)
    @ResponseBody
    public Object fileSaveContent(String path,String filecontent) throws IOException {
        fileReadService.SaveFile(path,filecontent);
        return returnCallback("success",filecontent);
    }

    @RequestMapping(value = {"/etcList"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object productList(HttpServletRequest request) throws UnsupportedEncodingException {
        String idString = request.getParameter("id");
        String eventIdString = request.getParameter("eventId");
        if(!StringUtils.isNotEmpty(eventIdString)){
            return null;
        }
        Integer eventId = Integer.parseInt(eventIdString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectById(eventId);

        idString = new String(idString.getBytes("ISO-8859-1"),"utf-8");
        /** 初始化页面 加载，有root信息，返回 jsTreeDataRoots */
        if (idString.equals("#")) {
            /** 根节点为0 */
            idString= "/home/saas/deploy_tmp/"+nebulaPublishEvent.getPublishProductKey()+"/src_svn/etc/";
//            idString="F:\\121";
            Map<String,Boolean> Srcmap=fileAnalyzeService.getDirMapByDirPath(idString);
            List<JsTreeDataRoot> jsTreeDataRoots = new ArrayList<JsTreeDataRoot>();
            JsTreeDataRoot jsTreeDataRoot=new JsTreeDataRoot();
            jsTreeDataRoot.setId(idString+"D");
            jsTreeDataRoot.setText("Root");
            JsTreeDataState jsTreeDataState=new JsTreeDataState(false, false, false);
            jsTreeDataRoot.setState(jsTreeDataState);


            /** 设置子节点列表*/

            List<JsTreeData> Childrens =new ArrayList<>();
            Set<String> keys=Srcmap.keySet();
            Iterator<String> it=keys.iterator();
            while(it.hasNext()) {
                String path = it.next();
                JsTreeData Children = new JsTreeData();
                Children.setParent(idString);
                Children.setText(path);
                JsTreeDataState childtreeDataState = new JsTreeDataState(false, false, false);
                Children.setState(jsTreeDataState);
                if(Srcmap.get(path)){
//                    Children.setId(idString + "\\" + path + "D");
                    Children.setId(idString + "/" + path + "D");
                    Map<String,Boolean> hasChild=fileAnalyzeService.getDirMapByDirPath(idString + "/" + path);
                    if(hasChild.size()>0){
                        Children.setChildren(true);
                    }
                    else
                        Children.setChildren(false);
                }
                else {
                    Children.setChildren(false);
//                    Children.setId(idString+"\\"+path+"F");
                    Children.setId(idString+"/"+path+"F");
                    Children.setIcon("jstree-file-icon");
                }
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
        String parentId=idString;
        idString=idString.substring(0,idString.length()-1);
        Map<String,Boolean> childSrcmap=fileAnalyzeService.getDirMapByDirPath(idString);
        List<JsTreeData> jsTreeDatas =new ArrayList<>();
        Set<String> ckey=childSrcmap.keySet();
        Iterator<String> cit=ckey.iterator();
        while(cit.hasNext()) {
            String path=cit.next();
            JsTreeData jsTreeData=new JsTreeData();
            jsTreeData.setParent(parentId);
            jsTreeData.setText(path);
            JsTreeDataState cjsTreeDataState=new JsTreeDataState(false, false, false);
            jsTreeData.setState(cjsTreeDataState);
            if(childSrcmap.get(path)){
                jsTreeData.setId(idString+"/"+path+"D");
//                jsTreeData.setId(idString+"\\"+path+"D");
                Map<String,Boolean> hasChild=fileAnalyzeService.getDirMapByDirPath(idString+"/"+path);
                if(hasChild.size()>0){
                    jsTreeData.setChildren(true);
                }
                else
                    jsTreeData.setChildren(false);
            }
            else {
                jsTreeData.setId(idString+"/"+path+"F");
//                jsTreeData.setId(idString+"\\"+path+"F");
                jsTreeData.setChildren(false);
                jsTreeData.setIcon("jstree-file-icon");
            }
            jsTreeDatas.add(jsTreeData);
        }

        return jsTreeDatas;
    }

    @RequestMapping(value="/newFile",method=RequestMethod.POST)
    @ResponseBody
    public Object newFile(String type,String fileName) throws IOException {
        String tip="目录";
        if("file".equals(type)){
            tip="文件";
        }
        if(fileReadService.newDirOrFile(type,fileName)){
            return returnCallback("Success",tip+"创建成功");
        }
        return returnCallback("Error",tip+"创建失败，"+tip+"已存在");
    }

    //文件变更查看页面
    @RequestMapping(value = "/checkList.htm",method = RequestMethod.GET)
    public String checkList(Model model){
        String idString = request.getParameter("eventId");
        if(StringUtils.isEmpty(idString)){
            model.addAttribute("error","id为空");
        }else{
            Integer id = Integer.parseInt(idString);
            NebulaPublishEvent publishEvent = publishEventService.selectById(id);
            List<FileChangeData> fileChangeDatas = publishEventService.changeListJsonStringToList(publishEvent.getChangeList());
            model.addAttribute("fileChangeDatas",fileChangeDatas);
            model.addAttribute("eventId",id);
        }
        return "event/changeListCheck";
    }
}
