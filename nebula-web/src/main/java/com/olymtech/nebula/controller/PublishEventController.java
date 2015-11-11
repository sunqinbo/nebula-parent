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
import com.olymtech.nebula.entity.enums.PublishActionGroup;
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
        //判断动作属于哪个组
        List<NebulaPublishSchedule> nebulaPublishSchedules=publishScheduleService.selectByEventId(id);
        int last=nebulaPublishSchedules.size();
        if(last!=0) {
            String action= String.valueOf(nebulaPublishSchedules.get(last - 1).getPublishAction());
            PublishAction actionName = PublishAction.valueOf(action);
            Boolean actionState= nebulaPublishSchedules.get(last - 1).getIsSuccessAction();
            NebulaPublishSequence nebulaPublishSequence=publishSequenceService.selectByActionName(actionName);
            int whichstep = -2;
            switch (nebulaPublishSequence.getActionGroup()){
                case PRE_MASTER:whichstep=0;break;
                case PRE_MINION:whichstep=1;break;
                case PUBLISH_REAL:whichstep=2;break;
                case FAIL_END:whichstep=3;break;
                case SUCCESS_END:whichstep=-1;break;
            }
            model.addAttribute("whichstep",whichstep);
            model.addAttribute("action",action);
            model.addAttribute("actionState",actionState);
        }
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
    public Callback checkPublishScheduleByEventId(HttpServletRequest request){
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
    public Callback preMasterPublish(HttpServletRequest request){
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
            Dispatcher dispatcher = new Dispatcher(chain);
            dispatcher.doDispatch(nebulaPublishEvent);
            return returnCallback("Success","发布准备完成");
        } catch (Exception e) {
            logger.error("prePublishMaster error:",e);
        }
        return returnCallback("Error","发布准备出现错误");
    }

    @RequestMapping(value="/publish_event/preMinionPublish.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Callback preMinionPublish(HttpServletRequest request){
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
            Dispatcher dispatcher = new Dispatcher(chain);
            dispatcher.doDispatch(nebulaPublishEvent);
        } catch (Exception e) {
            logger.error("prePublishMinion error:",e);
            return returnCallback("Success","预发布出现错误");
        }
        return returnCallback("Success","预发布完成");
    }

    @RequestMapping(value="/publish_event/publishReal.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishReal(HttpServletRequest request){
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
            Dispatcher dispatcher = new Dispatcher(chain);
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
                Dispatcher dispatcher = new Dispatcher(chain);
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
        List<NebulaPublishSchedule> nebulaPublishSchedules=publishScheduleService.selectByEventId(eventId);
        int last=nebulaPublishSchedules.size();
        Map<String, Object> map = new HashMap<>();
        if(last>0) {
            String actionNameString= String.valueOf(nebulaPublishSchedules.get(last - 1).getPublishAction());
            PublishAction actionName = PublishAction.valueOf(actionNameString);
            Boolean actionState= nebulaPublishSchedules.get(last - 1).getIsSuccessAction();
            NebulaPublishSequence nebulaPublishSequence=publishSequenceService.selectByActionName(actionName);
            int whichstep = -2;
            switch (nebulaPublishSequence.getActionGroup()){
                case PRE_MASTER:whichstep=0;break;
                case PRE_MINION:whichstep=1;break;
                case PUBLISH_REAL:whichstep=2;break;
                case FAIL_END:whichstep=3;break;
                case SUCCESS_END:whichstep=-1;break;
            }
            map.put("actionName",actionName);
            map.put("actionState",actionState);
            map.put("whichstep",whichstep);
        }
        return returnCallback("Success",map);
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
