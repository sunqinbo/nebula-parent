package com.olymtech.nebula.controller;

import com.aliyuncs.cdn.model.v20141111.DescribeRefreshTasksResponse;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesResponse;
import com.aliyuncs.slb.model.v20140515.DescribeHealthStatusResponse;
import com.aliyuncs.slb.model.v20140515.DescribeLoadBalancerAttributeResponse;
import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.common.utils.DateUtils;
import com.olymtech.nebula.core.action.Action;
import com.olymtech.nebula.core.action.ActionChain;
import com.olymtech.nebula.core.action.Dispatcher;
import com.olymtech.nebula.core.googleauth.GoogleAuthFactory;
import com.olymtech.nebula.core.utils.SpringUtils;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.entity.enums.LogAction;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.entity.enums.PublishStatus;
import com.olymtech.nebula.file.analyze.IFileAnalyzeService;
import com.olymtech.nebula.service.*;
import com.olymtech.nebula.service.action.*;
import com.olymtech.nebula.service.starry.IStarryCdnApi;
import com.olymtech.nebula.service.starry.IStarrySlbApi;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    INebulaPublishModuleService publishModuleService;
    @Resource
    private IPublishSlbService publishSlbService;
    @Resource
    HttpServletRequest request;
    @Resource
    IUserService userService;
    @Resource
    private IStarryCdnApi starryCdnApi;
    @Resource
    private IStarrySlbApi starrySlbApi;
    @Resource
    private IElkLogService elkLogService;
    @Resource
    private IFileAnalyzeService fileAnalyzeService;
    @Resource
    private IFileReadService fileReadService;
    @Resource
    private IPublishEventLogService publishEventLogService;

    @Value("${master_deploy_dir}")
    private String MasterDeployDir;


    @RequiresPermissions("publishevent:page")
    @RequestMapping(value = {"/list"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object PublishList(DataTablePage dataTablePage, NebulaPublishEvent nebulaPublishEvent) throws Exception {
        PageInfo pageInfo = publishEventService.getPublishEvent(dataTablePage, nebulaPublishEvent);
        return pageInfo;
    }

    @RequestMapping(value = "/event.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String publishEvent(Model model) throws Exception {
        List<ProductTree> productTrees = analyzeArsenalApiService.getBuProductTreeList();
        List<ProductTree> productTreeList = new ArrayList<>();
        NebulaUserInfo user = getLoginUser();
        NebulaUserInfo userInfoWithRoles = userService.getAclUserWithRolesByEmpId(user.getEmpId());
        List<AclRole> aclRoleList = userInfoWithRoles.getAclRoles();
        Boolean flag = false;
        for (AclRole aclRole : aclRoleList) {
            if (aclRole.getRoleName().equals("root") || aclRole.getRoleName().equals("admin")) {
                flag = true;
            }
        }
        if (flag) {
            model.addAttribute("productTrees", productTrees);
        } else {
            for (ProductTree productTree : productTrees) {
                if (productTree.getNodeName().equals(getLoginUser().getBu())) {
                    productTreeList.add(productTree);
                }
            }
            model.addAttribute("productTrees", productTreeList);
        }
        return "event/publishEvent";
    }

    @RequestMapping(value = "/productTreeList/pid", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Callback getProductTreeListByPid(Integer pid) throws Exception {
        List<ProductTree> productTrees = analyzeArsenalApiService.getProductTreeListByPid(pid);
        return returnCallback("Success", productTrees);
    }

    @RequiresPermissions("publishevent:page")
    @RequestMapping(value = "/list.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String publishList(Model model) throws Exception {
        List<ProductTree> productTrees = analyzeArsenalApiService.getProductTreeListByPid(2);
        model.addAttribute("productTrees", productTrees);
        return "event/publishList";
    }

    @RequiresPermissions("publishevent:page")
    @RequestMapping(value = "/approveList.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String publishApproveList(Model model) throws Exception {
        List<ProductTree> productTrees = analyzeArsenalApiService.getProductTreeListByPid(2);
        model.addAttribute("productTrees", productTrees);
        return "event/publishApproveList";
    }

    @RequiresPermissions("publishevent:page")
    @RequestMapping(value = "/process.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public String publishProcess(HttpServletRequest request, Model model) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));//发布事件的ID；
//        NebulaPublishEvent nebulaPublishEvent=  publishEventService.selectWithChildByEventId(id);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectById(id);
        Integer submitEmpId = nebulaPublishEvent.getSubmitEmpId();
        Integer publishEmpId = nebulaPublishEvent.getPublishEmpId();
        Integer approveEmpId = nebulaPublishEvent.getApproveEmpId();
        if (submitEmpId != null) {
            nebulaPublishEvent.setSubmitUser(userService.selectByEmpId(submitEmpId));
        }
        if (publishEmpId != null) {
            nebulaPublishEvent.setPublishUser(userService.selectByEmpId(publishEmpId));
        }
        if (approveEmpId != null) {
            nebulaPublishEvent.setApproveUser(userService.selectByEmpId(approveEmpId));
        }
        List<NebulaPublishModule> publishModules = publishModuleService.selectByEventId(id);
        List<FileChangeData> fileChangeDatas = publishEventService.changeListJsonStringToList(nebulaPublishEvent.getChangeList());
        model.addAttribute("fileChangeDatas", fileChangeDatas);
        model.addAttribute("Modules", publishModules);
        model.addAttribute("Event", nebulaPublishEvent);
        model.addAttribute("LastEventId", publishEventService.getLastPublishId(id));
        return "event/publishProcess";
    }

    /**
     * public event
     */
    @RequiresPermissions("publishapply:commit")
    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public Object createPublishEvent(NebulaPublishEvent nebulaPublishEvent) throws Exception {
        String publishSvn = nebulaPublishEvent.getPublishSvn();
        String pattern = "svn://svn.olymtech.com/warspace/";
        Pattern p = Pattern.compile(pattern);
        Matcher match = p.matcher(publishSvn);
        if (!match.find()) {
            return returnCallback("Error", "请检测svn地址（svn://svn.olymtech.com/warspace/）");
        }
        Integer empId = getLoginUser().getEmpId();
        nebulaPublishEvent.setSubmitEmpId(empId);
        nebulaPublishEvent.setPublishStatus(PublishStatus.PENDING_APPROVE);

        int id = publishEventService.createPublishEvent(nebulaPublishEvent);
        publishEventLogService.logPublishAction(nebulaPublishEvent.getId(), LogAction.APPLY_PUBLISH_EVENT, getLoginUser().getNickname() + "申请创建发布事件成功!", getLoginUser().getEmpId());
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

    /**
     * 发布准备
     */
    @RequiresPermissions("publishevnt:prePublishMaster")
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

        /*判断登录人是否是提交人,是否是管理员,是否是超级管理员*/
        NebulaUserInfo loginUser = getLoginUser();
        if (!userService.ifLoginUserValid(loginUser, nebulaPublishEvent)) {
            return returnCallback("Error", "对不起,您没有该权限!");
        }

        //创建任务队列
        ActionChain chain = new ActionChain();
        chain.addAction(SpringUtils.getBean(GetPublishSvnAction.class));
        chain.addAction(SpringUtils.getBean(PublishRelationAction.class));
        chain.addAction(SpringUtils.getBean(GetSrcSvnAction.class));

        Dispatcher dispatcher = new Dispatcher(chain, request, response);
        dispatcher.doDispatch(nebulaPublishEvent);

        /** 修改发布状态 */
        nebulaPublishEvent.setPublishStatus(PublishStatus.PENDING_PUBLISH);
        nebulaPublishEvent.setPublishDatetime(new Date());
        nebulaPublishEvent.setPublishEmpId(getLoginUser().getEmpId());
        publishEventService.update(nebulaPublishEvent);
        publishEventLogService.logPublishAction(eventId, LogAction.PUBLISH_PREPARATION, "发布准备完成", loginUser.getEmpId());
        return returnCallback("Success", "发布准备执行完成");
    }

    /**
     * 启动预发布
     */
    @RequiresPermissions("publishevnt:prePublishMinion")
    @RequestMapping(value = "/preMinionPublish", method = {RequestMethod.POST})
    @ResponseBody
    public Callback prePublishMinion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);
        nebulaPublishEvent.setPublishActionGroup(PublishActionGroup.PRE_MINION);

        /*判断登录人是否是提交人,是否是管理员,是否是超级管理员*/
        NebulaUserInfo loginUser = getLoginUser();
        if (!userService.ifLoginUserValid(loginUser, nebulaPublishEvent)) {
            return returnCallback("Error", "对不起,您没有该权限!");
        }

        /** 修改发布状态 */
        nebulaPublishEvent.setPublishStatus(PublishStatus.PUBLISHING);
        publishEventService.update(nebulaPublishEvent);

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
        publishEventLogService.logPublishAction(eventId, LogAction.START_PRE_PUBLISH,"启动预发布成功",loginUser.getEmpId());
        return returnCallback("Success", "预发布完成");
    }

    /**
     * 启动正式发布
     */
    @RequiresPermissions("publishevnt:publishReal")
    @RequestMapping(value = "/publishReal", method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishReal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idString = request.getParameter("id");
        String totpCode = request.getParameter("totpCode");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "id is null");
        }
        Integer eventId = Integer.parseInt(idString);
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);

        /*判断登录人是否是提交人,是否是管理员,是否是超级管理员*/
        NebulaUserInfo loginUser = getLoginUser();
        if (!userService.ifLoginUserValid(loginUser, nebulaPublishEvent)) {
            return returnCallback("Error", "对不起,您没有该权限!");
        }

        /*如果是生产发布,需要做验证*/
        if (nebulaPublishEvent.getPublishEnv().equals("product")) {
            NebulaUserInfo userInfo = getLoginUser();
            try {
                Integer totpCodeInt = Integer.parseInt(totpCode);
                Boolean isCodeValid = GoogleAuthFactory.authoriseUser(userInfo.getUsername(), totpCodeInt);
                if (!isCodeValid) {
                    return returnCallback("Error", "验证码错误");
                }
            } catch (Exception e) {
                return returnCallback("Error", "验证码错误");
            }
        }
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

        publishEventLogService.logPublishAction(eventId, LogAction.START_FORMAL_PUBLISH,"启动正式发布成功",loginUser.getEmpId());

        return returnCallback("Success", "预发布完成");
    }

    /**
     * 单步发布重试
     */
    @RequiresPermissions("publishevnt:publishContinue")
    @RequestMapping(value = "/publishContinue", method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishContinue() throws Exception {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "参数id为空");
        }
        try {
            Integer eventId = Integer.parseInt(idString);
            NebulaPublishEvent nebulaPublishEvent = publishEventService.selectWithChildByEventId(eventId);

            /*判断登录人是否是提交人,是否是管理员,是否是超级管理员之一*/
            NebulaUserInfo loginUser = getLoginUser();
            if (!userService.ifLoginUserValid(loginUser, nebulaPublishEvent)) {
                return returnCallback("Error", "对不起,您没有该权限!");
            }

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
                nebulaPublishEvent.setPublishActionGroup(publishSequence.getActionGroup());
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

            if (chain.getActions().size() != 0) {
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

    /**
     * 确认发布成功
     */
    @RequiresPermissions("publishevnt:publishSuccessEnd")
    @RequestMapping(value = "/publishSuccessEnd", method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishSuccessEnd() throws Exception {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "参数id为空");
        }
        try {
            Integer eventId = Integer.parseInt(idString);
            NebulaPublishEvent publishEvent = publishEventService.selectWithChildByEventId(eventId);
            publishEvent.setPublishActionGroup(PublishActionGroup.SUCCESS_END);

            /*判断登录人是否是提交人,是否是管理员,是否是超级管理员*/
            NebulaUserInfo loginUser = getLoginUser();
            if (!userService.ifLoginUserValid(loginUser, publishEvent)) {
                return returnCallback("Error", "对不起,您没有该权限!");
            }

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
            publishEventService.updateLogCountSum(true, PublishStatus.PUBLISHED, publishEvent);
            publishEventLogService.logPublishAction(eventId, LogAction.CONFIRM_SUCCESS, "确认发布成功", loginUser.getEmpId());
            return returnCallback("Success", "成功发布确认成功");
        } catch (Exception e) {
            logger.error("publishSuccessEnd error:", e);
        }
        return returnCallback("Error", "成功发布确认失败");
    }

    /**
     * 回滚
     */
    @RequiresPermissions("publishevnt:publishFailEnd")
    @RequestMapping(value = "/publishFailEnd", method = {RequestMethod.POST})
    @ResponseBody
    public Callback publishFailEnd() throws Exception {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "参数id为空");
        }
        try {
            Integer eventId = Integer.parseInt(idString);
            NebulaPublishEvent publishEvent = publishEventService.selectWithChildByEventId(eventId);
            publishEvent.setPublishActionGroup(PublishActionGroup.FAIL_END);

            /*判断登录人是否是提交人,是否是管理员,是否是超级管理员*/
            NebulaUserInfo loginUser = getLoginUser();
            if (!userService.ifLoginUserValid(loginUser, publishEvent)) {
                return returnCallback("Error", "对不起,您没有该权限!");
            }

            /*如果是生产,判断用户是否是超级管理员*/
            if (publishEvent.getPublishEnv().equals("product")) {
                if (!userService.userRoleIsNeedRole(loginUser, "root")) {
                    return returnCallback("Error", "对不起,您不是超级管理员,没有该权限!");
                }
            }

            //创建任务队列
            ActionChain chain = new ActionChain();
            chain.addAction(SpringUtils.getBean(StopTomcatAction.class));
            chain.addAction(SpringUtils.getBean(ChangeFailLnAction.class));
            chain.addAction(SpringUtils.getBean(StartTomcatAction.class));
            chain.addAction(SpringUtils.getBean(CleanFailDirAction.class));
            chain.addAction(SpringUtils.getBean(CleanPublishDirAction.class));

            Dispatcher dispatcher = new Dispatcher(chain, request, response);
            dispatcher.doDispatch(publishEvent);

            /** 清楚基线 */
            publishBaseService.cleanBaseByEventId(eventId);

            /** 更新事件单为 失败发布 */
            publishEventService.updateLogCountSum(false, PublishStatus.ROLLBACK, publishEvent);
            publishEventLogService.logPublishAction(eventId, LogAction.ROLL_BACK, "回滚成功", loginUser.getEmpId());
            return returnCallback("Success", "失败发布确认成功");
        } catch (Exception e) {
            logger.error("publishFailEnd error:", e);
        }
        return returnCallback("Error", "服务器异常");
    }

    /**
     * 回退重发
     * 不会创建新的发布事件，本次事件重发
     */
    @RequiresPermissions("publishevnt:retryPublishRollback")
    @RequestMapping(value = "/retryPublishRollback", method = {RequestMethod.POST})
    @ResponseBody
    public Callback retryPublishRollback(HttpServletRequest request) {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "参数id为空");
        }
        try {
            Integer eventId = Integer.parseInt(idString);
            publishEventService.retryPublishRollback(eventId);
            NebulaPublishEvent publishEvent = publishEventService.selectById(eventId);
            publishEvent.setPublishStatus(PublishStatus.PENDING_PRE);

            /*判断登录人是否是提交人,是否是管理员,是否是超级管理员*/
            NebulaUserInfo loginUser = getLoginUser();
            if (!userService.ifLoginUserValid(loginUser, publishEvent)) {
                return returnCallback("Error", "对不起,您没有该权限!");
            }

            publishEventService.update(publishEvent);
            publishEventLogService.logPublishAction(eventId, LogAction.RE_PUBLISH, "重新发布成功", getLoginUser().getEmpId());
            return returnCallback("Success", "重新发布回退成功");
        } catch (Exception e) {
            logger.error("retryPublishRollback error:", e);
        }
        return returnCallback("Error", "重新发布回退失败");
    }

    /**
     * 重启tomcat
     */
    @RequiresPermissions("publishevnt:publishReal")
    @RequestMapping(value = "/restartTomcat", method = {RequestMethod.POST})
    @ResponseBody
    public Callback restartTomcat(HttpServletRequest request) {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "参数id为空");
        }
        try {
            Integer eventId = Integer.parseInt(idString);
            NebulaPublishEvent publishEvent = publishEventService.selectWithChildByEventId(eventId);
            publishEvent.setPublishActionGroup(PublishActionGroup.RESTART_TOMCAT);

            /*判断登录人是否是提交人,是否是管理员,是否是超级管理员*/
            NebulaUserInfo loginUser = getLoginUser();
            if (!userService.ifLoginUserValid(loginUser, publishEvent)) {
                return returnCallback("Error", "对不起,您没有该权限!");
            }

            //创建任务队列
            ActionChain chain = new ActionChain();
            chain.addAction(SpringUtils.getBean(StopTomcatAction.class));
            chain.addAction(SpringUtils.getBean(StartTomcatAction.class));

            Dispatcher dispatcher = new Dispatcher(chain, request, response);
            dispatcher.doDispatch(publishEvent);

            return returnCallback("Success", "重启tomcat成功");
        } catch (Exception e) {
            logger.error("restartTomcat error:", e);
        }
        return returnCallback("Error", "服务器异常");
    }

    /**
     * 取消发布
     */
    @RequiresPermissions("publishevnt:publishReal")
    @RequestMapping(value = "/cancelPublish", method = {RequestMethod.POST})
    @ResponseBody
    public Callback cancelpublish(HttpServletRequest request) {
        String idString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(idString)) {
            return returnCallback("Error", "参数id为空");
        }
        try {
            Integer eventId = Integer.parseInt(idString);
            NebulaPublishEvent publishEvent = publishEventService.selectWithChildByEventId(eventId);
            publishEvent.setPublishActionGroup(PublishActionGroup.CANCEL_END);

            /*判断登录人是否是提交人,是否是管理员,是否是超级管理员*/
            NebulaUserInfo loginUser = getLoginUser();
            if (!userService.ifLoginUserValid(loginUser, publishEvent)) {
                return returnCallback("Error", "对不起,您没有该权限!");
            }

            //创建任务队列
            ActionChain chain = new ActionChain();
            chain.addAction(SpringUtils.getBean(CleanFailDirAction.class));
            chain.addAction(SpringUtils.getBean(CleanPublishDirAction.class));

            Dispatcher dispatcher = new Dispatcher(chain, request, response);
            dispatcher.doDispatch(publishEvent);

            /** 更新事件单为 失败发布 */
            publishEvent.setIsSuccessPublish(false);
            publishEvent.setPublishStatus(PublishStatus.CANCEL);
            publishEventService.update(publishEvent);

            return returnCallback("Success", "取消发布确认成功");
        } catch (Exception e) {
            logger.error("cancelPublish error:", e);
        }
        return returnCallback("Error", "服务器异常");
    }

    /**
     * 确认编辑etc
     */
    @RequiresPermissions("publishevnt:updateEtcEnd")
    @RequestMapping(value = "/updateEtcEnd", method = {RequestMethod.POST})
    @ResponseBody
    public Callback updateEtcEnd(HttpServletRequest request) throws Exception {
        String eventIdString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(eventIdString)) {
            return returnCallback("Error", "id参数为空");
        }

        Integer eventId = Integer.parseInt(eventIdString);
        NebulaPublishEvent publishEvent = publishEventService.selectWithChildByEventId(eventId);

        Boolean result = publishEventService.updateChangeList(publishEvent);
        if (!result) {
            return returnCallback("Error", "获取配置变更列表失败");
        }

        /** 生产环境 编辑etc后，需要审核 */
        if (publishEvent.getPublishEnv().equals("product")) {
            publishScheduleService.logScheduleByAction(eventId, PublishAction.UPDATE_ETC, PublishActionGroup.PRE_MASTER, true, "");

            /** 配置变更为空（配置无变更），不需要审批 */
            NebulaPublishEvent publishEventInDB = publishEventService.selectWithChildByEventId(eventId);
            if(publishEventInDB.getChangeList().equals("{}")){
                publishScheduleService.logScheduleByAction(eventId, PublishAction.ETC_APPROVE, PublishActionGroup.PRE_MASTER, true, "");
            }else{
                publishScheduleService.logScheduleByAction(eventId, PublishAction.ETC_APPROVE, PublishActionGroup.PRE_MASTER, null, "");
            }
        } else {
            publishScheduleService.logScheduleByAction(eventId, PublishAction.UPDATE_ETC, PublishActionGroup.PRE_MASTER, true, "");
            publishScheduleService.logScheduleByAction(eventId, PublishAction.ETC_APPROVE, PublishActionGroup.PRE_MASTER, true, "");
        }
        publishEventLogService.logPublishAction(eventId, LogAction.FINISH_ETC_EDIT,"完成配置编辑",getLoginUser().getEmpId());
        return returnCallback("Success", "完成配置编辑");
    }

    @RequiresPermissions("publishevnt:etcApprove")
    @RequestMapping(value = "/etcApprovePass", method = {RequestMethod.POST})
    @ResponseBody
    public Callback etcApprovePass(HttpServletRequest request) throws Exception {
        String eventIdString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(eventIdString)) {
            return returnCallback("Error", "id参数为空");
        }
        Integer eventId = Integer.parseInt(eventIdString);
        publishScheduleService.logScheduleByAction(eventId, PublishAction.ETC_APPROVE, PublishActionGroup.PRE_MASTER, true, "");
        publishEventLogService.logPublishAction(eventId, LogAction.CONFIGURATION_APPROVAL, "配置审批通过", getLoginUser().getEmpId());
        return returnCallback("Success", "审批通过");
    }

    @RequiresPermissions("publishevnt:etcApprove")
    @RequestMapping(value = "/etcApproveReject", method = {RequestMethod.POST})
    @ResponseBody
    public Callback etcApproveReject(HttpServletRequest request) throws Exception {
        String eventIdString = request.getParameter("id");
        if (!StringUtils.isNotEmpty(eventIdString)) {
            return returnCallback("Error", "id参数为空");
        }
        Integer eventId = Integer.parseInt(eventIdString);
        NebulaPublishSchedule etcApproveSchedule = publishScheduleService.selectEntryAction(eventId, PublishAction.ETC_APPROVE, PublishActionGroup.PRE_MASTER);
        NebulaPublishSchedule updateEtcSchedule = publishScheduleService.selectEntryAction(eventId, PublishAction.UPDATE_ETC, PublishActionGroup.PRE_MASTER);

        if (etcApproveSchedule == null) {
            return returnCallback("Error", "审批驳回失败：审批流程获取失败");
        }

        if (updateEtcSchedule == null) {
            return returnCallback("Error", "审批驳回失败：编辑配置流程获取失败");
        }

        /** 删除审核流程，回到编辑配置流程 */
        publishScheduleService.deleteById(etcApproveSchedule.getId());

        /** 编辑etc设置成null，未开始 */
        updateEtcSchedule.setIsSuccessAction(null);
        publishScheduleService.update(updateEtcSchedule);

        return returnCallback("Success", "审批驳回");
    }


    @RequestMapping(value = "/publishProcessStep", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object publishProcessGetStep(Integer eventId) throws Exception {
        String[] group1 = {"GET_PUBLISH_SVN", "ANALYZE_PROJECT", "GET_SRC_SVN", "UPDATE_ETC", "ETC_APPROVE"};
        String[] group2 = {"CREATE_PUBLISH_DIR", "COPY_PUBLISH_OLD_ETC", "COPY_PUBLISH_OLD_WAR", "PUBLISH_NEW_ETC", "PUBLISH_NEW_WAR"};
        String[] group3 = {"STOP_TOMCAT", "CHANGE_LN", "START_TOMCAT"};
        String[] group4 = {"STOP_TOMCAT", "CHANGE_LN", "START_TOMCAT", "CLEAN_FAIL_DIR"};
        String[] group5 = {"CLEAN_HISTORY_DIR", "UPDATE_SRC_SVN"};
        String[] group6 = {"CLEAN_PUBLISH_DIR"};
        String[] group7 = {"STOP_TOMCAT", "START_TOMCAT"};
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
                case "CLEAN_END":
                    actionGroup = 6;
                    group = group6;
                    break;
                case "RESTART_TOMCAT":
                    actionGroup = 7;
                    group = group7;
                    break;
            }
            for (int i = 0; i < group.length; i++) {
                if (actionNameString.equals(group[i])) {
                    whichStep = i + 1;
                }
            }
            int lastGroup = 0;
            if (last > 2) {
                NebulaPublishSchedule nebulaPublishScheduleLast = nebulaPublishSchedules.get(last - 2);
                switch (String.valueOf(nebulaPublishScheduleLast.getPublishActionGroup())) {
                    case "PRE_MASTER":
                        lastGroup = 1;
                        break;
                    case "PRE_MINION":
                        lastGroup = 2;
                        break;
                    case "PUBLISH_REAL":
                        lastGroup = 3;
                        break;
                    case "FAIL_END":
                        lastGroup = 4;
                        break;
                    case "SUCCESS_END":
                        lastGroup = 5;
                        break;
                    case "CLEAN_END":
                        lastGroup = 6;
                        break;
                    case "RESTART_TOMCAT":
                        lastGroup = 7;
                        break;
                }
            }
            map.put("lastGroup", lastGroup);
            map.put("actionGroup", actionGroup);
            map.put("whichStep", whichStep);
            map.put("actionState", actionState);
            map.put("errorMsg", nebulaPublishSchedule.getErrorMsg());
            map.put("errorType", nebulaPublishSchedule.getErrorType());
        }
        //获取机器信息
        if (eventId != null) {
            List<NebulaPublishHost> nebulaPublishHosts = publishHostService.selectByEventIdAndModuleId(eventId, null);
            NebulaPublishEvent nebulaPublishEvent = publishEventService.selectById(eventId);
            for (NebulaPublishHost nebulaPublishHost : nebulaPublishHosts) {
                nebulaPublishHost.setLogNumber(publishEventService.getPublishLogHostLogCount(nebulaPublishEvent, nebulaPublishHost, "ERROR"));
                nebulaPublishHost.setExcNumber(publishEventService.getPublishLogHostLogCount(nebulaPublishEvent, nebulaPublishHost, "EXCEPTION"));
            }
            map.put("HostInfos", nebulaPublishHosts);
            map.put("eventStatus", nebulaPublishEvent.getPublishStatus());
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

    /**
     * 发布升级
     */
    @RequiresPermissions("publishevnt:addnextpublish")
    @RequestMapping(value = "/add/nextpublish", method = {RequestMethod.POST})
    @ResponseBody
    public Object addNextPublish(Integer eventId, String nowPublish) throws Exception {
        NebulaPublishEvent publishEvent = publishEventService.selectById(eventId);

        NebulaPublishEvent newPublishEvent = new NebulaPublishEvent();

        newPublishEvent.setPublishSubject(publishEvent.getPublishSubject());
        newPublishEvent.setPublishBuName(publishEvent.getPublishBuName());
        newPublishEvent.setPublishBuCname(publishEvent.getPublishBuCname());
        newPublishEvent.setPublishProductName(publishEvent.getPublishProductName());
        newPublishEvent.setPublishProductCname(publishEvent.getPublishProductCname());
        newPublishEvent.setPublishEnv(nowPublish);
        newPublishEvent.setPublishSvn(publishEvent.getPublishSvn());
        newPublishEvent.setProductSrcSvn(publishEvent.getProductSrcSvn());
        newPublishEvent.setPublishStatus(PublishStatus.PENDING_APPROVE);
        newPublishEvent.setSubmitEmpId(getLoginUser().getEmpId());
        int id = publishEventService.createPublishEvent(newPublishEvent);

        publishEvent.setPid(id);
        publishEventService.updateByIdSelective(publishEvent);

        publishEventLogService.logPublishAction(eventId, LogAction.ENTER_NEXT_PUBLISH, "发布升级完成", getLoginUser().getEmpId());
        return returnCallback("Success", id);
    }

    /**
     * 发布审批
     */
    @RequiresPermissions("publishevent:approval")
    @RequestMapping(value = "/update/approval", method = {RequestMethod.POST})
    @ResponseBody
    public Object approvalPublish(Integer eventId) throws Exception {
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectById(eventId);
        NebulaUserInfo user = getLoginUser();
        String publishEnv = nebulaPublishEvent.getPublishEnv();
        /*如果是生产发布,判断登录人的角色是否是部门主管的角色*/
        if (publishEnv.equals("product")) {
            Boolean userRoleIsExamine = userService.userRoleIsNeedRole(user, "examine");
            if (!userRoleIsExamine) {
                return returnCallback("Error", "审批人必须是部门主管才能审批!");
            }
        }
        nebulaPublishEvent.setIsApproved(true);
        nebulaPublishEvent.setPublishStatus(PublishStatus.PENDING_PRE);
        nebulaPublishEvent.setApproveEmpId(user.getEmpId());
        nebulaPublishEvent.setApproveDatetime(new Date());
        publishEventService.update(nebulaPublishEvent);

        publishEventLogService.logPublishAction(eventId, LogAction.PUBLISH_APPROVE, "发布审批通过", getLoginUser().getEmpId());
        return returnCallback("Success", "");
    }

    /**
     * 删除发布事件
     */
    @RequiresPermissions("publishevnt:delete")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    @ResponseBody
    public Object deletePublish(Integer eventId) throws Exception {
        NebulaPublishEvent nebulaPublishEvent = publishEventService.selectById(eventId);
        nebulaPublishEvent.setIsDelete(true);
        publishEventService.update(nebulaPublishEvent);
        return returnCallback("Success", "");
    }

    /**
     * 查询发布事件
     */
    @RequestMapping(value = "/get/noPublish", method = {RequestMethod.POST})
    @ResponseBody
    public Object getPublishStatus(NebulaPublishEvent nebulaPublishEvent) throws Exception {
        return publishEventService.isPUBLISHING(nebulaPublishEvent);
    }

    /**
     * 获取模块信息
     */
    @RequestMapping(value = "/list/moduleAndApps", method = {RequestMethod.POST})
    @ResponseBody
    public Object getmoduleAndApps(Integer eventId) {
        List<NebulaPublishModule> publishModules = new ArrayList<>();
        publishModules = publishModuleService.selectByEventId(eventId);
        return publishModules;
    }

    /**
     * 获取cdn刷新列表
     */
    @RequestMapping(value = "/list/describeRefreshTasks", method = {RequestMethod.POST})
    @ResponseBody
    public Object getDescribeRefreshTasks() {
        DescribeRefreshTasksResponse describeRefreshTasksResponse = starryCdnApi.describeRefreshTasks("olymtech@aliyun.com", "cn-hangzhou");
        if (describeRefreshTasksResponse != null) {
            return returnCallback("Success", describeRefreshTasksResponse);
        } else {
            return returnCallback("Error", "获取cdn刷新列表失败");
        }
    }

    /**
     * 刷新cdn
     */
    @RequestMapping(value = "/add/refreshObjectCaches", method = {RequestMethod.POST})
    @ResponseBody
    public Object refreshObjectCaches(String objectPath, String objectType) {
        String[] object = {"http://pstage.200jit.com/", "http://pptest.200jit.com/", "http://pmtest.200jit.com/", "http://patest.200jit.com/", "http://www.cargopm.com/"};
        List<String> objectPathList = Arrays.asList(object);
        Boolean result = objectPathList.contains(objectPath);
        if (!result) {
            return returnCallback("Error", "您提交的地址，不在刷新列表内");
        }
        RefreshObjectCachesResponse refreshObjectCachesResponse = starryCdnApi.refreshObjectCaches("olymtech@aliyun.com", "cn-hangzhou", objectPath, objectType);
        if (refreshObjectCachesResponse != null) {
            return returnCallback("Success", refreshObjectCachesResponse);
        } else {
            return returnCallback("Error", "刷新cdn刷新列表失败");
        }
    }

    /**
     * 所有slb状态列表
     *
     * @param eventId 发布id
     * @return
     */
    @RequestMapping(value = "/list/describeLoadBalancerAttributes", method = {RequestMethod.POST})
    @ResponseBody
    public Object getLoadBalancerAttribute(Integer eventId) {
        List<NebulaPublishSlb> publishSlbs = publishSlbService.selectByPublishEventId(eventId);
        for (NebulaPublishSlb publishSlb : publishSlbs) {
            DescribeLoadBalancerAttributeResponse loadBalancerAttributeResponse = starrySlbApi.describeLoadBalancerAttribute(publishSlb);
            publishSlb.setDescribeLoadBalancerAttributeResponse(loadBalancerAttributeResponse);
            DescribeHealthStatusResponse describeHealthStatusResponse = starrySlbApi.describeHealthStatusTasks(publishSlb);
            publishSlb.setDescribeHealthStatusResponse(describeHealthStatusResponse);
        }
        return returnCallback("Success", publishSlbs);
    }

    /**
     * 获取一个slb的机器状态
     *
     * @param id nebulaPublishSlb.id
     * @return
     */
    @RequestMapping(value = "/list/describeHealthStatusTasks", method = {RequestMethod.POST})
    @ResponseBody
    public Object getDescribeHealthStatusTasks(Integer id) {
        NebulaPublishSlb publishSlb = publishSlbService.selectById(id);
        DescribeHealthStatusResponse describeHealthStatusResponse = starrySlbApi.describeHealthStatusTasks(publishSlb);
        publishSlb.setDescribeHealthStatusResponse(describeHealthStatusResponse);
        return returnCallback("Success", publishSlb);
    }


    /**
     * 获取log count
     */
//    @RequestMapping(value="/log/getPublishLogCount",method={RequestMethod.POST})
//    @ResponseBody
//    public Object getPublishLogCount(Integer eventId){
//        NebulaPublishEvent publishEvent = (NebulaPublishEvent) publishEventService.getPublishEventById(eventId);
//        /** 存放错误数量map */
//        Map<String,Object> map = new HashMap<>();
//        List<NebulaPublishHost> nebulaPublishHosts = publishHostService.selectByEventIdAndModuleId(eventId, null);
//        for(NebulaPublishHost publishHost:nebulaPublishHosts){
//            map.put("host", publishHost);
//            Date fromDate = DateUtils.getDateByGivenHour(publishEvent.getPublishDatetime(),-8);
//            Date toDate = DateUtils.getDateByGivenHour(new Date(),-8);
//            ElkSearchData elkSearchData = new ElkSearchData(publishHost.getPassPublishHostName(),
//                    "Error",fromDate,toDate,1,10);
//
//            Integer total = elkLogService.count(elkSearchData);
//            map.put("total", total);
//        }
//        return returnCallback("Success", map);
//    }
    /*public int getPublishLogHostLogCount(NebulaPublishEvent publishEvent, NebulaPublishHost publishHost, String logType) {
//        NebulaPublishEvent publishEvent = (NebulaPublishEvent) publishEventService.getPublishEventById(eventId);

        if (publishEvent.getPublishDatetime() == null) {
            return 0;
        }
        Date fromDate = DateUtils.getDateByGivenHour(publishEvent.getPublishDatetime(), -8);
        Date toDate = DateUtils.getDateByGivenHour(new Date(), -8);
        ElkSearchData elkSearchData = new ElkSearchData(publishHost.getPassPublishHostName(), logType, fromDate, toDate, 1, 10);
        return elkLogService.count(elkSearchData,publishEvent.getPublishEnv());
    }
        return elkLogService.count(elkSearchData);
    }*/

    /**
     * 获取log 详细信息
     */
    @RequestMapping(value = "/log/getPublishLogByHost", method = {RequestMethod.POST})
    @ResponseBody
    public Object getPublishLogByHost(Integer eventId, ElkSearchData elkSearchDataReuqest) {
        NebulaPublishEvent publishEvent = publishEventService.selectById(eventId);
        if (!StringUtils.isNotEmpty(elkSearchDataReuqest.getHost())) {
            return returnCallback("Error", "host参数为必选项");
        }

        Date fromDate = DateUtils.getDateByGivenHour(publishEvent.getPublishDatetime(), -8);
        Date toDate = DateUtils.getDateByGivenHour(DateUtils.strToDate(elkSearchDataReuqest.getToDateString()), -8);
        /** 如果 已发布 已回滚 已取消 ，toDate重新设置为发布结束时间 */
        if (PublishStatus.PUBLISHED == publishEvent.getPublishStatus() || PublishStatus.ROLLBACK == publishEvent.getPublishStatus() || PublishStatus.CANCEL == publishEvent.getPublishStatus()) {
            if (publishEvent.getPublishEndDatetime() != null) {
                toDate = DateUtils.getDateByGivenHour(publishEvent.getPublishEndDatetime(), -8);
            }
        }

        if (elkSearchDataReuqest.getPageNum() == null) {
            elkSearchDataReuqest.setPageNum(1);
        }
        ElkSearchData elkSearchData = new ElkSearchData(elkSearchDataReuqest.getHost(),
                elkSearchDataReuqest.getKeyWord(), fromDate, toDate, elkSearchDataReuqest.getPageNum(), elkSearchDataReuqest.getPageSize());
        PageInfo pageInfo = elkLogService.search(elkSearchData, publishEvent.getPublishEnv());
        return returnCallback("Success", pageInfo);
    }

    /**
     * 获取发布事件上一阶段的id
     */
    @RequestMapping(value = "/getLastPublishId", method = {RequestMethod.POST})
    @ResponseBody
    public Object getLastPublishId(Integer eventId) {
        int lastEventId = publishEventService.getLastPublishId(eventId);
        return returnCallback("Success", lastEventId);
    }

    /**
     * 判断验证码是否正确
     */
    @RequestMapping(value = "/isTotpCodeValid", method = {RequestMethod.POST})
    @ResponseBody
    public Object isTotpCodeValid(String code) {
        try {
            Integer codeInt = Integer.parseInt(code);
            NebulaUserInfo user = getLoginUser();
            Boolean isCodeValid = GoogleAuthFactory.authoriseUser(user.getUsername(), codeInt);
            if (isCodeValid) {
                return returnCallback("Success", "验证码正确有效.");
            } else {
                return returnCallback("Error", "验证码错误.");
            }
        } catch (Exception e) {
            return returnCallback("Error", "验证码错误.");
        }
    }

    /**
     * 删除非war包文件
     */
    @RequestMapping(value = "/deleteErrorFiles", method = {RequestMethod.POST})
    @ResponseBody
    public Object deleteErrorFiles(Integer eventId) {
        NebulaPublishEvent event = publishEventService.selectById(eventId);
        String publicWarDirPath = MasterDeployDir + "/" + event.getPublishProductKey() + "/publish_war/";
        List<String> appNameList = fileAnalyzeService.getFileListByDirPath(publicWarDirPath);
        String regex = ".*\\.war$";
        List<String> errorNameList = new ArrayList<>();
        Integer removedNum = 0;
        for (int i = 0; i < appNameList.size(); i++) {
            if (!appNameList.get(i).matches(regex)) {
                errorNameList.add(appNameList.get(i));
                Boolean removed = fileAnalyzeService.rmFile(event.getPublishProductKey(), appNameList.get(i));
                if (removed) {
                    removedNum++;
                }
            }
        }
        if (removedNum == errorNameList.size()) {
            return returnCallback("Success", "删除非war包文件成功.");
        }
        return returnCallback("Error", "删除非war包文件失败.");
    }

    /**
     * 仅生产环境下可对编辑etc的配置进行查看
     */
    @RequestMapping(value = "/viewEtcContent", method = {RequestMethod.POST})
    @ResponseBody
    public Object viewEtcContent(Integer eventId, String key) {
        NebulaPublishEvent publishEvent = publishEventService.selectWithChildByEventId(eventId);
        String publishEtcPath = MasterDeployDir + publishEvent.getPublishProductKey() + "/src_svn/etc" + key;
        String oldEtcPath = MasterDeployDir + publishEvent.getPublishProductKey() + "/src_etc" + key;
//        String dirSrcPath = "F:\\home\\saas\\deploy_tmp\\" + publishEvent.getPublishProductKey() + "\\src_svn\\etc" + key;
//        String destPath = "F:\\home\\saas\\deploy_tmp\\" + publishEvent.getPublishProductKey() + "\\src_etc" + key;
        if (!publishEvent.getPublishEnv().equals("product")) {
            return returnCallback("Error", "发布环境不是生产");
        }
        Map<String, List<String>> map = new HashMap<>();
        try {
            List<String> publishFileContent = fileReadService.ReadFile(publishEtcPath);
            List<String> oldFileContent = fileReadService.ReadFile(oldEtcPath);
            map.put("publishFileContent", publishFileContent);
            map.put("oldFileContent", oldFileContent);
        } catch (IOException e) {
            return returnCallback("Error", "文件内容解析异常");
        }
        return returnCallback("Success", map);
    }
}
