package com.olymtech.nebula.controller;

import com.olymtech.nebula.core.action.ActionChain;
import com.olymtech.nebula.core.action.Dispatcher;
import com.olymtech.nebula.entity.Callback;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishSchedule;
import com.olymtech.nebula.service.IPublishEventService;
import com.olymtech.nebula.service.IPublishScheduleService;
import com.olymtech.nebula.service.action.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    HttpServletRequest request;

    @RequestMapping(value="/publishEvent.htm",method= {RequestMethod.POST,RequestMethod.GET})
     public String publishEvent(){
        return "publishEvent";
    }

    @RequestMapping(value="/publishList.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String publishList(){
        return "publishList";
    }

    @RequestMapping(value="/publishProcess.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String publishProcess(){
        return "publishProcess";
    }

    /**
     * public event
     * */
    @RequestMapping(value="/createPublishEvent.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Object createPublishEvent(NebulaPublishEvent nebulaPublishEvent){
        if (publishEventService.createPublishEvent(nebulaPublishEvent)<1) {
            return returnCallback("Error", "create failure");
        }
        return returnCallback("Success","create Success");
    }

    /**
     * public schedule
     * */
    @RequestMapping(value="/publish/checkPublishSchedule.htm",method = {RequestMethod.POST})
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

    @RequestMapping(value="/publish/prePublishMaster.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Callback prePublishMaster(HttpServletRequest request){
        String idString = request.getParameter("id");
        if(!StringUtils.isNotEmpty(idString)){
            return returnCallback("Error","id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectById(eventId);
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(new GetPublishSvnAction());
        chain.addAction(new PublishRelationAction());
        chain.addAction(new GetSrcSvnAction());

        try {
            Dispatcher dispatcher = new Dispatcher(chain);
            dispatcher.doDispatch(nebulaPublishEvent);
        } catch (Exception e) {
            logger.error("prePublishMaster error:",e);
            return returnCallback("Success","发布准备出现错误");
        }
        return returnCallback("Success","发布准备完成");
    }

    @RequestMapping(value="/publish/prePublishMinion.htm",method = {RequestMethod.POST})
    @ResponseBody
    public Callback prePublishMinion(HttpServletRequest request){
        String idString = request.getParameter("id");
        if(!StringUtils.isNotEmpty(idString)){
            return returnCallback("Error","id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(new CreateDirAciton());
        //chain.addAction(new CpEtcWarAction());
        //chain.addAction(new PublishNewAction());

        try {
            Dispatcher dispatcher = new Dispatcher(chain);
            dispatcher.doDispatch(nebulaPublishEvent);
        } catch (Exception e) {
            logger.error("prePublishMinion error:",e);
            return returnCallback("Success","预发布出现错误");
        }
        return returnCallback("Success","预发布完成");
    }

    @RequestMapping(value="/publish/publishReal.htm",method = {RequestMethod.POST})
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
            return returnCallback("Success","预发布出现错误");
        }
        return returnCallback("Success","预发布完成");
    }



}
