package com.olymtech.nebula.controller;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.core.action.Action;
import com.olymtech.nebula.core.action.ActionChain;
import com.olymtech.nebula.core.action.Dispatcher;
import com.olymtech.nebula.core.utils.SpringUtils;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.service.*;
import com.olymtech.nebula.service.action.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WYQ on 2015/11/3.
 */
@Controller
@RequestMapping("/publish")
public class PublishController extends BaseController {

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
    IPublishBaseService publishBaseService;
    @Resource
    HttpServletRequest request;

    @RequestMapping(value = {"/list"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object PublishList(DataTablePage dataTablePage) {
        PageInfo pageInfo = publishEventService.getPublishEvent(dataTablePage);
        return pageInfo;
    }

    @RequestMapping(value = "/event", method = {RequestMethod.POST, RequestMethod.GET})
    public String publishEvent(Model model) throws Exception {
        List<ProductTree> productTrees = analyzeArsenalApiService.getProductTreeListByPid(2);
        model.addAttribute("productTrees", productTrees);
        return "event/publishEvent";
    }

    @RequestMapping(value = "/getProductTreeList/pid", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback getProductTreeListByPid(Integer pid) throws Exception {
        List<ProductTree> productTrees = analyzeArsenalApiService.getProductTreeListByPid(pid);
        return returnCallback("Success", productTrees);
    }

    @RequestMapping(value = "/list.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String publishList() throws Exception {
        return "event/publishList";
    }

    @RequestMapping(value = "/publishProcess.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String publishProcess(HttpServletRequest request, Model model) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));//发布事件的ID；
//        NebulaPublishEvent nebulaPublishEvent=  publishEventService.selectWithChildByEventId(id);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectById(id);
        model.addAttribute("Event", nebulaPublishEvent);
        return "event/publishProcess";
    }

    /**
     * public event
     */
    @RequestMapping(value = "/createPublishEvent", method = {RequestMethod.POST})
    @ResponseBody
    public Object createPublishEvent(NebulaPublishEvent nebulaPublishEvent) {
        int id = publishEventService.createPublishEvent(nebulaPublishEvent);
        return returnCallback("Success", id);
    }

    /**
     * public schedule
     */
    @RequestMapping(value = "/checkPublishSchedule", method = {RequestMethod.POST})
    @ResponseBody
    public Callback checkPublishScheduleByEventId(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        List<NebulaPublishSchedule> nebulaPublishSchedules = publishScheduleService.selectByEventId(eventId);
        return returnCallback("Success", nebulaPublishSchedules);
    }

    @RequestMapping(value = "/preMasterPublish", method = {RequestMethod.POST})
    @ResponseBody
    public Callback prePublishMaster(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);
        nebulaPublishEvent.setPublishActionGroup(PublishActionGroup.PRE_MASTER);
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(SpringUtils.getBean(GetPublishSvnAction.class));
        chain.addAction(SpringUtils.getBean(PublishRelationAction.class));
        chain.addAction(SpringUtils.getBean(GetSrcSvnAction.class));

        Dispatcher dispatcher = new Dispatcher(chain, request, response);
        dispatcher.doDispatch(nebulaPublishEvent);
        return returnCallback("Success", "发布准备执行完成");
    }

    @RequestMapping(value = "/preMinionPublish", method = {RequestMethod.POST})
    @ResponseBody
    public Callback prePublishMinion(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);
        nebulaPublishEvent.setPublishActionGroup(PublishActionGroup.PRE_MINION);
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(SpringUtils.getBean(CreateDirAciton.class));
        chain.addAction(SpringUtils.getBean(CpEtcAction.class));
        chain.addAction(SpringUtils.getBean(CpWarAction.class));
        chain.addAction(SpringUtils.getBean(PublishEtcAction.class));
        chain.addAction(SpringUtils.getBean(PublishWarAction.class));

        try {
            Dispatcher dispatcher = new Dispatcher(chain, request, response);
            dispatcher.doDispatch(nebulaPublishEvent);
        } catch (Exception e) {
            logger.error("publishReal error:", e);
            return returnCallback("Error", "预发布出现错误");
        }
        return returnCallback("Success", "预发布完成");
    }

    @RequestMapping(value = "/publishReal", method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishReal(HttpServletRequest request, HttpServletResponse response) {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);
        nebulaPublishEvent.setPublishActionGroup(PublishActionGroup.PUBLISH_REAL);
        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(SpringUtils.getBean(StopTomcatAction.class));
        chain.addAction(SpringUtils.getBean(ChangeLnAction.class));
        chain.addAction(SpringUtils.getBean(StartTomcatAction.class));

        try {
            Dispatcher dispatcher = new Dispatcher(chain, request, response);
            dispatcher.doDispatch(nebulaPublishEvent);
        } catch (Exception e) {
            logger.error("publishReal error:", e);
            return returnCallback("Error", "预发布出现错误");
        }
        return returnCallback("Success", "预发布完成");
    }

    @RequestMapping(value = "/publishContinue", method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishContinue() {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "参数id为空");
        }
        try {

            Integer eventId = Integer.parseInt(idString);
            NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);
            List<NebulaPublishSchedule> nebulaPublishSchedules = publishScheduleService.selectByEventId(eventId);
            if (nebulaPublishSchedules == null || nebulaPublishSchedules.size() == 0) {
                return returnCallback("Error", "无法获取发布事件进度");
            }
            Integer size = nebulaPublishSchedules.size();
            NebulaPublishSchedule publishSchedule = nebulaPublishSchedules.get(size - 1);
            List<NebulaPublishSequence> publishSequences = publishSequenceService.selectByActionGroup(publishSchedule.getPublishActionGroup());

            //创建任务队列
            ActionChain chain = new ActionChain();
            Boolean flag = false;
            for (NebulaPublishSequence publishSequence : publishSequences) {
                if (publishSequence.getActionName() == publishSchedule.getPublishAction()) {
                    flag = true;
                }
                if (publishSequence.getActionClass() == null || "".equals(publishSequence.getActionClass())) {
                    continue;
                }
                if (flag) {
                    String actionClassName = publishSequence.getActionClass();
                    actionClassName = "com.olymtech.nebula.service.action." + actionClassName;
                    Action action = (Action) SpringUtils.getBean(Class.forName(actionClassName));
                    chain.addAction(action);
                }
            }

            if (chain != null) {
                Dispatcher dispatcher = new Dispatcher(chain, request, response);
                dispatcher.doDispatch(nebulaPublishEvent);
                return returnCallback("Success", "继续发布完成");
            } else {
                return returnCallback("Error", "继续发布链为空");
            }
        } catch (Exception e) {
            logger.error("publishContinue error:", e);
        }
        return returnCallback("Error", "继续发布出错");
    }

    @RequestMapping(value = "/publishSuccessEnd", method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishSuccessEnd() {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "参数id为空");
        }
        try {
            Integer eventId = Integer.parseInt(idString);
            NebulaPublishEvent publishEvent = publishEventService.selectWithChildByEventId(eventId);
            publishEvent.setPublishActionGroup(PublishActionGroup.SUCCESS_END);
            //创建任务队列
            ActionChain chain = new ActionChain();
            chain.addAction(SpringUtils.getBean(CleanHistoryDirAction.class));
            chain.addAction(SpringUtils.getBean(UpdateSrcSvnAction.class));
            chain.addAction(SpringUtils.getBean(CleanPublishDirAction.class));

            Dispatcher dispatcher = new Dispatcher(chain, request, response);
            dispatcher.doDispatch(publishEvent);

            /** 发布成功基线 */
            for (NebulaPublishModule publishModule : publishEvent.getPublishModules()) {
                NebulaPublishBase publishBase = new NebulaPublishBase(eventId,
                        publishEvent.getPublishProductName(),
                        publishEvent.getPublishEnv(),
                        publishModule.getPublishModuleName(),
                        publishModule.getPublishModuleKey());
                publishBaseService.insertAndUpdate(publishBase);
            }

            /** 更新事件单为 成功发布 */
            publishEvent.setIsSuccessPublish(true);
            publishEventService.update(publishEvent);

            return returnCallback("Success", "成功发布确认成功");
        } catch (Exception e) {
            logger.error("publishSuccessEnd error:", e);
        }
        return returnCallback("Error", "成功发布确认失败");
    }

    @RequestMapping(value = "/publishFailEnd", method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishFailEnd() {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "参数id为空");
        }
        try {
            Integer eventId = Integer.parseInt(idString);
            NebulaPublishEvent publishEvent = publishEventService.selectWithChildByEventId(eventId);
            publishEvent.setPublishActionGroup(PublishActionGroup.FAIL_END);
            //创建任务队列
            ActionChain chain = new ActionChain();
            chain.addAction(SpringUtils.getBean(StopTomcatAction.class));
            chain.addAction(SpringUtils.getBean(ChangeFailLnAction.class));
            chain.addAction(SpringUtils.getBean(StartTomcatAction.class));
            chain.addAction(SpringUtils.getBean(CleanFailDirAction.class));
            chain.addAction(SpringUtils.getBean(CleanPublishDirAction.class));

            Dispatcher dispatcher = new Dispatcher(chain, request, response);
            dispatcher.doDispatch(publishEvent);

            /** 更新事件单为 失败发布 */
            publishEvent.setIsSuccessPublish(false);
            publishEventService.update(publishEvent);

            return returnCallback("Success", "失败发布确认成功");
        } catch (Exception e) {
            logger.error("publishFailEnd error:", e);
        }
        return returnCallback("Error", "服务器异常");
    }

    @RequestMapping(value = "/retryPublishRollback", method = {RequestMethod.POST})
    @ResponseBody
    public Callback retryPublishRollback(HttpServletRequest request) {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "参数id为空");
        }
        try {
            publishEventService.retryPublishRollback(Integer.parseInt(idString));
            return returnCallback("Success", "重新发布回退成功");
        } catch (Exception e) {
            logger.error("retryPublishRollback error:", e);
        }
        return returnCallback("Error", "重新发布回退失败");
    }

    @RequestMapping(value = "/updateEtcEnd", method = {RequestMethod.POST})
    @ResponseBody
    public Callback updateEtcEnd(HttpServletRequest request) {
        String eventId = request.getParameter("id");
        if (!StringUtils.isNotEmpty(eventId)) {
            return returnCallback("Error", "id参数为空");
        }
        publishScheduleService.logScheduleByAction(Integer.parseInt(eventId), PublishAction.UPDATE_ETC, PublishActionGroup.PRE_MASTER, true, "");
        return returnCallback("Success", "完成ETC编辑");
    }

    @RequestMapping(value = "/publishProcessStep", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object publishProcessGetStep(Integer eventId) {
        String[] group1 = {"GET_PUBLISH_SVN", "ANALYZE_PROJECT", "GET_SRC_SVN", "UPDATE_ETC"};
        String[] group2 = {"CREATE_PUBLISH_DIR", "COPY_PUBLISH_OLD_ETC", "COPY_PUBLISH_OLD_WAR", "PUBLISH_NEW_ETC", "PUBLISH_NEW_WAR"};
        String[] group3 = {"STOP_TOMCAT", "CHANGE_LN", "START_TOMCAT"};
        String[] group4 = {"STOP_TOMCAT", "CHANGE_LN", "START_TOMCAT"};
        String[] group5 = {"CLEAR_HISTORY_DIR", "UPDATE_SRC_SVN"};
        List<NebulaPublishSchedule> nebulaPublishSchedules = publishScheduleService.selectByEventId(eventId);
        int last = nebulaPublishSchedules.size();
        Map<String, Object> map = new HashMap<>();
        if (last != 0) {
            NebulaPublishSchedule nebulaPublishSchedule = nebulaPublishSchedules.get(last - 1);
            String actionNameString = String.valueOf(nebulaPublishSchedule.getPublishAction());
            Boolean actionState = nebulaPublishSchedule.getIsSuccessAction();
            int actionGroup = -1;
            int whichStep = 0;
            String[] group = new String[8];
            switch (String.valueOf(nebulaPublishSchedule.getPublishActionGroup())) {
                case "PRE_MASTER":
                    actionGroup = 1;
                    group = group1;
                    break;
                case "PRE_MINION":
                    actionGroup = 2;
                    group = group2;
                    break;
                case "PUBLISH_REAL":
                    actionGroup = 3;
                    group = group3;
                    break;
                case "FAIL_END":
                    actionGroup = 4;
                    group = group4;
                    break;
                case "SUCCESS_END":
                    actionGroup = 5;
                    group = group5;
                    break;
            }
            for (int i = 0; i < group.length; i++) {
                if (actionNameString.equals(group[i])) {
                    whichStep = i + 1;
                }
            }
            map.put("actionGroup", actionGroup);
            map.put("whichStep", whichStep);
            map.put("actionState", actionState);
            map.put("errorMsg", nebulaPublishSchedule.getErrorMsg());
        }
        //获取机器信息
        if(eventId!=null) {
            List<NebulaPublishHost> nebulaPublishHosts = publishHostService.selectByEventIdAndModuleId(eventId, null);
            map.put("HostInfos", nebulaPublishHosts);
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
