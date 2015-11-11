package com.olymtech.nebula.controller;

import com.olymtech.nebula.core.action.Action;
import com.olymtech.nebula.core.action.ActionChain;
import com.olymtech.nebula.core.action.Dispatcher;
import com.olymtech.nebula.core.utils.SpringUtils;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishSchedule;
import com.olymtech.nebula.entity.ProductTree;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.service.IAnalyzeArsenalApiService;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.service.IPublishEventService;
import com.olymtech.nebula.service.IPublishHostService;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.olymtech.nebula.service.IPublishSequenceService;
import com.olymtech.nebula.service.action.*;
import org.apache.commons.lang.StringUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WYQ on 2015/11/3.
 */
@Controller("publishEventController")
@RequestMapping("/")
public class PublishEventController extends BaseController{

    @Resource
    IPublishEventService publishEventService;
    @Resource
    IPublishScheduleService publishScheduleService;
    @Resource
    IAnalyzeArsenalApiService analyzeArsenalApiService;
    @Resource
    IPublishHostService publishHostService;
    @Resource
    IPublishSequenceService publishSequenceService;
    @Resource
    HttpServletRequest request;

    @RequestMapping(value="/publishEvent.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String publishEvent(Model model){
        List<ProductTree> productTrees = analyzeArsenalApiService.getProductTreeListByPid(2);
        model.addAttribute("productTrees",productTrees);
        return "publishEvent";
    }

    @RequestMapping(value="/publish_event/getProductTreeListByPid.htm",method= {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Callback getProductTreeListByPid(Integer pid){
        try{
            List<ProductTree> productTrees = analyzeArsenalApiService.getProductTreeListByPid(pid);
            return returnCallback("Success",productTrees);
        }catch (Exception e){
            logger.error("getProductTreeListByPid error:",e);
        }
        return returnCallback("Error","服务端错误");
    }

    @RequestMapping(value="/publishList.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String publishList(){
        return "publishList";
    }

    @RequestMapping(value="/publishProcess.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String publishProcess(HttpServletRequest request,Model model){
        int id = Integer.parseInt(request.getParameter("id"));//发布事件的ID；
        NebulaPublishEvent nebulaPublishEvent=  publishEventService.selectWithChildByEventId(id);
        model.addAttribute("Event",nebulaPublishEvent);
        return "publishProcess";
    }

    /**
     * public event
     * */
    @RequestMapping(value="/publish_event/createPublishEvent.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Object createPublishEvent(NebulaPublishEvent nebulaPublishEvent){
        try{
            int id = publishEventService.createPublishEvent(nebulaPublishEvent);
            return returnCallback("Success",id);
        }catch (Exception e){
            logger.error("createPublishEvent error:",e);
        }
        return returnCallback("Error","create Error");
    }

    /**
     * public schedule
     * */
    @RequestMapping(value="/publish_event/checkPublishSchedule.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Callback checkPublishScheduleByEventId(HttpServletRequest request, HttpServletResponse response){
        String idString = request.getParameter("id");
        if(!StringUtils.isNotEmpty(idString)){
            return returnCallback("Error","id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        List<NebulaPublishSchedule> nebulaPublishSchedules = publishScheduleService.selectByEventId(eventId);
        return returnCallback("Success",nebulaPublishSchedules);
    }

    @RequestMapping(value="/publish_event/preMasterPublish.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Callback prePublishMaster(HttpServletRequest request, HttpServletResponse response){
        String idString = request.getParameter("id");
        if(!StringUtils.isNotEmpty(idString)){
            return returnCallback("Error","id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectById(eventId);
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(SpringUtils.getBean(GetPublishSvnAction.class));
        chain.addAction(SpringUtils.getBean(PublishRelationAction.class));
        chain.addAction(SpringUtils.getBean(GetSrcSvnAction.class));

        try {
            Dispatcher dispatcher = new Dispatcher(chain,request,response);
            dispatcher.doDispatch(nebulaPublishEvent);
            return returnCallback("Success","发布准备执行完成");
        } catch (Exception e) {
            logger.error("prePublishMaster error:",e);
        }
        return returnCallback("Error","发布准备执行出现错误");
    }

    @RequestMapping(value="/publish_event/preMinionPublish.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Callback prePublishMinion(HttpServletRequest request, HttpServletResponse response){
        String idString = request.getParameter("id");
        if(!StringUtils.isNotEmpty(idString)){
            return returnCallback("Error","id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(new CreateDirAciton());
        chain.addAction(new CpEtcAction());
        chain.addAction(new CpWarAction());
        chain.addAction(new PublishEtcAction());
        chain.addAction(new PublishWarAction());

        try {
            Dispatcher dispatcher = new Dispatcher(chain,request,response);
            dispatcher.doDispatch(nebulaPublishEvent);
        } catch (Exception e) {
            logger.error("prePublishMinion error:",e);
            return returnCallback("Success","预发布出现错误");
        }
        return returnCallback("Success","预发布完成");
    }

    @RequestMapping(value="/publish_event/publishReal.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishReal(HttpServletRequest request, HttpServletResponse response){
        String idString = request.getParameter("id");
        if(!StringUtils.isNotEmpty(idString)){
            return returnCallback("Error","id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(new StopTomcatAction());
        chain.addAction(new ChangeLnAction());
        chain.addAction(new StartTomcatAction());

        try {
            Dispatcher dispatcher = new Dispatcher(chain,request,response);
            dispatcher.doDispatch(nebulaPublishEvent);
        } catch (Exception e) {
            logger.error("publishReal error:",e);
            return returnCallback("Error","预发布出现错误");
        }
        return returnCallback("Success","预发布完成");
    }

    @RequestMapping(value="/publish_event/publishContinue.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishContinue(){
        String idString = request.getParameter("id");
        if(!StringUtils.isNotEmpty(idString)){
            return returnCallback("Error","参数id为空");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);
        List<NebulaPublishSchedule> nebulaPublishSchedules = publishScheduleService.selectByEventId(eventId);
        if(nebulaPublishSchedules != null){
            return returnCallback("Error","无法获取发布事件进度");
        }
        Integer size = nebulaPublishSchedules.size();
        NebulaPublishSchedule publishSchedule = nebulaPublishSchedules.get(size - 1);
        List<NebulaPublishSequence> publishSequences = publishSequenceService.selectByActionGroup(publishSchedule.getPublishActionGroup());

        try{
            //创建任务队列
            ActionChain chain = new ActionChain();
            for(NebulaPublishSequence publishSequence:publishSequences){
                if( (publishSequence.getActionName() == publishSchedule.getPublishAction()) &&
                        (publishSequence.getActionClass()!=null && !"".equals(publishSequence.getActionClass())) ){
                    String actionClassName = publishSequence.getActionClass();
                    Action action =  (Action) SpringUtils.getBean(Class.forName(actionClassName));
                    chain.addAction(action);
                }
            }

            if(chain != null){
                Dispatcher dispatcher = new Dispatcher(chain,request,response);
                dispatcher.doDispatch(nebulaPublishEvent);
                return returnCallback("Success","继续发布完成");
            }else{
                return returnCallback("Error","继续发布链为空");
            }
        }catch (Exception e){
            logger.error("publishContinue error:",e);
        }
        return returnCallback("Error","继续发布出错");
    }

    @RequestMapping(value="/publishProcessStep.htm",method= {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object publishProcessGetStep(Integer eventId){
        String[] group1={"GET_PUBLISH_SVN","ANALYZE_PROJECT","GET_SRC_SVN","UPDATE_ETC"};
        String[] group2={"CREATE_PUBLISH_DIR","COPY_PUBLISH_OLD_ETC","COPY_PUBLISH_OLD_WAR","PUBLISH_NEW_ETC","PUBLISH_NEW_WAR"};
        String[] group3={"STOP_TOMCAT","CHANGE_LN","START_TOMCAT"};
        String[] group4={"FDELETE_LN","FSTOP_TOMCAT","FCREATE_LN","FSTART_TOMCAT","FCLEAR_PUBLISH_DIR"};
        List<NebulaPublishSchedule> nebulaPublishSchedules=publishScheduleService.selectByEventId(eventId);
        int last=nebulaPublishSchedules.size();
        Map<String, Object> map = new HashMap<>();
        if(last!=0) {
            NebulaPublishSchedule nebulaPublishSchedule=nebulaPublishSchedules.get(last - 1);
            String actionNameString= String.valueOf(nebulaPublishSchedule.getPublishAction());
            Boolean actionState= nebulaPublishSchedule.getIsSuccessAction();
            int actionGroup = -1;
            int whichStep=0;
            String[] group = new String[8];
            switch (String.valueOf(nebulaPublishSchedule.getPublishActionGroup())){
                case "PRE_MASTER":actionGroup=1;group=group1;break;
                case "PRE_MINION":actionGroup=2;group=group2;break;
                case "PUBLISH_REAL":actionGroup=3;group=group3;break;
                case "FAIL_CLEAR":actionGroup=4;group=group4;break;
                case "SUCCESS_CLEAR":actionGroup=0;break;
            }
            for (int i = 0; i < group.length; i++) {
                if(actionNameString.equals(group[i])){
                    whichStep=i+1;
                }
            }
            map.put("actionGroup", actionGroup);
            map.put("whichStep", whichStep);
            map.put("actionState", actionState);
        }
        return returnCallback("Success", map);

    }

//    @RequestMapping(value="/whichStep.htm",method= {RequestMethod.POST,RequestMethod.GET})
//    @ResponseBody
//    public Object publishProcessGetWhichStep(Integer eventId){
//        List<NebulaPublishSchedule> nebulaPublishSchedules=publishScheduleService.selectByEventId(eventId);
//        int last=nebulaPublishSchedules.size();
//        if(last!=0) {
//            nebulaPublishSchedules.get(last - 1).getPublishAction();
//        }
//        return returnCallback("Success","");
//    }

}
