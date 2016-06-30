package com.olymtech.nebula.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.common.utils.DateUtils;
import com.olymtech.nebula.core.action.ActionChain;
import com.olymtech.nebula.core.action.Dispatcher;
import com.olymtech.nebula.dao.INebulaPublishEventDao;
import com.olymtech.nebula.entity.*;
import com.olymtech.nebula.entity.enums.PublishAction;
import com.olymtech.nebula.entity.enums.PublishActionGroup;
import com.olymtech.nebula.entity.enums.PublishStatus;
import com.olymtech.nebula.service.IFileAnalyzeService;
import com.olymtech.nebula.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.olymtech.nebula.common.utils.DateUtils.getKeyDate;

/**
 * Created by liwenji on 2015/11/4.
 */
@Service
public class PublishEventServiceImpl implements IPublishEventService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    INebulaPublishEventDao nebulaPublishEventDao;

    @Resource
    INebulaPublishModuleService nebulaPublishModuleService;

    @Resource
    IPublishAppService publishAppService;

    @Resource
    IPublishHostService publishHostService;

    @Autowired
    private IPublishScheduleService publishScheduleService;

    @Autowired
    private IPublishBaseService publishBaseService;

    @Resource
    private IElkLogService elkLogService;

    @Resource
    private IFileAnalyzeService fileAnalyzeService;

    @Value("${master_deploy_dir}")
    private String MasterDeployDir;


    @Override
    public void update(NebulaPublishEvent nebulaPublishEvent) {
        nebulaPublishEventDao.update(nebulaPublishEvent);
    }

    @Override
    public int createPublishEvent(NebulaPublishEvent nebulaPublishEvent) {
        Date now = new Date();
        String dateKey = getKeyDate(now);
        String key = nebulaPublishEvent.getPublishEnv() + "." + nebulaPublishEvent.getPublishProductName() + "." +
                dateKey;
        nebulaPublishEvent.setPublishProductKey(key);
        nebulaPublishEvent.setSubmitDatetime(now);

        /** 测试环境不需要审批 */
        String pEnvString = "test";
        Pattern pEnv = Pattern.compile(pEnvString);
        Matcher mEnv = pEnv.matcher(nebulaPublishEvent.getPublishEnv());
        if(mEnv.find()){
            nebulaPublishEvent.setIsApproved(true);
            nebulaPublishEvent.setPublishStatus(PublishStatus.PENDING_PRE);
            nebulaPublishEvent.setApproveEmpId(nebulaPublishEvent.getSubmitEmpId());
            nebulaPublishEvent.setApproveDatetime(new Date());
        }else {
            nebulaPublishEvent.setPublishStatus(PublishStatus.PENDING_APPROVE);
        }

        Integer id = nebulaPublishEventDao.insert(nebulaPublishEvent);
        publishScheduleService.logScheduleByAction(id, PublishAction.CREATE_PUBLISH_EVENT, PublishActionGroup
                .PRE_MASTER, true, "");
        return id;
    }

    @Override
    public PageInfo getPublishEvent(DataTablePage dataTablePage, NebulaPublishEvent nebulaPublishEvent) {
        PageHelper.startPage(dataTablePage.getPageNum(), dataTablePage.getPageSize());
        List<NebulaPublishEvent> nebulaPublishEvents = nebulaPublishEventDao.selectAllPagingWithUser
                (nebulaPublishEvent);
        PageInfo pageInfo = new PageInfo(nebulaPublishEvents);
        return pageInfo;
    }

    @Override
    public NebulaPublishEvent selectById(Integer id) {
        return nebulaPublishEventDao.selectById(id);
    }

    @Override
    public NebulaPublishEvent selectWithChildByEventId(Integer eventId) {
        NebulaPublishEvent nebulaPublishEvent = nebulaPublishEventDao.selectById(eventId);
        List<NebulaPublishModule> publishModules = nebulaPublishModuleService.selectByEventId(eventId);
        for (NebulaPublishModule publishModule : publishModules) {
            List<NebulaPublishApp> publishApps = publishAppService.selectByEventIdAndModuleId(eventId, publishModule
                    .getId());
            List<NebulaPublishHost> publishHosts = publishHostService.selectByEventIdAndModuleId(eventId,
                    publishModule.getId());
            publishModule.setPublishApps(publishApps);
            publishModule.setPublishHosts(publishHosts);
        }
        nebulaPublishEvent.setPublishModules(publishModules);
        return nebulaPublishEvent;
    }

    /**
     * 重新发布
     *
     * @param eventId
     * @return
     */
    @Override
    public Boolean retryPublishRollback(Integer eventId) {
        Boolean result = false;
        try {
            publishHostService.deleteByEventId(eventId);
            publishAppService.deleteByEventId(eventId);
            nebulaPublishModuleService.deleteByEventId(eventId);
            publishScheduleService.deleteByEventIdWithOutCreateAction(eventId);
            result = true;
        } catch (Exception e) {
            logger.error("retryPublishRollback error:", e);
        }
        return result;
    }

    @Override
    public List<NebulaPublishEvent> selectAllPagingWithUser(NebulaPublishEvent event) {
        return nebulaPublishEventDao.selectAllPagingWithUser(event);
    }

    @Override
    public Integer selectCountWithUser(NebulaPublishEvent event) {
        return nebulaPublishEventDao.selectCountWithUser(event);
    }

    @Override
    public List<NebulaPublishEvent> isPUBLISHING(NebulaPublishEvent nebulaPublishEvent) {
        List<NebulaPublishEvent> nebulaPublishEvents = new ArrayList<>();
        nebulaPublishEvents = nebulaPublishEventDao.selectNoPUBLISHING(nebulaPublishEvent);
        return nebulaPublishEvents;
    }

    @Override
    public int getLastPublishId(Integer eventId) {
        NebulaPublishEvent nebulaPublishEvent = new NebulaPublishEvent();
        nebulaPublishEvent.setPid(eventId);
        if (nebulaPublishEventDao.selectAllPaging(nebulaPublishEvent).size() > 0) {
            return nebulaPublishEventDao.selectAllPaging(nebulaPublishEvent).get(0).getId();
        } else
            return -1;
    }

    @Override
    public void updateByIdSelective(NebulaPublishEvent nebulaPublishEventReal) {
        nebulaPublishEventDao.updateByIdSelective(nebulaPublishEventReal);
    }

    /**
     * 更新错误数,发布状态
     */
    @Override
    public Boolean updateLogCountSum(Boolean isSuccessPublish, PublishStatus publishStatus, NebulaPublishEvent
            publishEvent) {

        publishEvent.setIsSuccessPublish(isSuccessPublish);
        publishEvent.setPublishStatus(publishStatus);
        /**先设置时间，这个在 getPublishLogHostLogCount 用到
         * PublishEndDatetime 为null 表示还没有发布完
         * PublishEndDatetime 不为null 表示发布已经完成
         * */
        publishEvent.setPublishEndDatetime(new Date());

        /**获取日志错误数,错误数等于所有机器总和*/
        Integer errorCountSum = 0;
        Integer exceptionCountSum = 0;
        List<NebulaPublishHost> nebulaPublishHosts = publishHostService.selectByEventIdAndModuleId(publishEvent.getId
                (), null);
        for (NebulaPublishHost nebulaPublishHost : nebulaPublishHosts) {
            Integer errorCount = getPublishLogHostLogCount(publishEvent, nebulaPublishHost, "ERROR");
            Integer exceptionCount = getPublishLogHostLogCount(publishEvent, nebulaPublishHost, "EXCEPTION");
            errorCountSum += errorCount;
            exceptionCountSum += exceptionCount;
        }
        publishEvent.setCountError(errorCountSum);
        publishEvent.setCountException(exceptionCountSum);
        nebulaPublishEventDao.update(publishEvent);
        return true;

    }

    /**
     * 获取log count
     */
    @Override
    public int getPublishLogHostLogCount(NebulaPublishEvent publishEvent, NebulaPublishHost publishHost, String
            logType) {

        if (publishEvent.getPublishDatetime() == null) {
            return 0;
        }
        Date fromDate = DateUtils.getDateByGivenHour(publishEvent.getPublishDatetime(), -8);
        /**
         * toDate 发布未结束 取 当前时间
         *        发布结束  取 发布完成时间
         */
        Date toDate = DateUtils.getDateByGivenHour(new Date(), -8);
        if (publishEvent.getPublishEndDatetime() != null) {
            toDate = DateUtils.getDateByGivenHour(publishEvent.getPublishEndDatetime(), -8);
        }
        ElkSearchData elkSearchData = new ElkSearchData(publishHost.getPassPublishHostName(), logType, fromDate,
                toDate, 1, 10);
        return elkLogService.count(elkSearchData, publishEvent.getPublishEnv());
    }

    /**
     * 更新changelist
     *
     * @param publishEvent
     * @return
     */
    @Override
    public Boolean updateChangeList(NebulaPublishEvent publishEvent) {
        try {
            String destPath = MasterDeployDir + publishEvent.getPublishProductKey() + "/src_svn/etc";
            String srcPath = MasterDeployDir + publishEvent.getPublishProductKey() + "/src_etc";
            String responseContext = fileAnalyzeService.getDirDiffList(srcPath, destPath);
            if (StringUtils.isEmpty(responseContext)) {
                return false;
            }
            publishEvent.setChangeList(responseContext);
            nebulaPublishEventDao.update(publishEvent);
            return true;
        } catch (Exception e) {
            logger.error("updateChangeList error:", e);
        }
        return false;
    }

    /**
     * {
     * "/a.properties": {
     * "change": "+dddd:3333\n",
     * "filename": "/a.properties",
     * "time": "2016-03-09 09:30:18.000000000"
     * },
     * "/arsenal.properties": {
     * "change": " ssss=3333\n-eeee=3333\n+cccc=4444\n",
     * "filename": "/arsenal.properties",
     * "time": "2016-03-09 09:29:27.000000000"
     * },
     * "/baidu/b.txt": {
     * "change": "-ddddddddddd\n",
     * "filename": "/baidu/b.txt",
     * "time": "1970-01-01 08:00:00.000000000"
     * }
     * }
     *
     * @param responseContext
     * @return
     */
    @Override
    public List<FileChangeData> changeListJsonStringToList(String responseContext) {

        List<FileChangeData> fileChangeDatas = new ArrayList<>();
        if (StringUtils.isEmpty(responseContext)) {
            return fileChangeDatas;
        }
        JSONObject jsonObject = JSONObject.parseObject(responseContext);
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String jsonString = entry.getValue().toString();
            JSONObject everyData = JSONObject.parseObject(jsonString);
            Map<String, String> everyMap = new HashMap<>();
            for (Map.Entry<String, Object> everyEntry : everyData.entrySet()) {
                everyMap.put(everyEntry.getKey(), String.valueOf(everyEntry.getValue()));
            }
            FileChangeData fileChangeData = new FileChangeData(everyMap.get("change"), everyMap.get("filename"),
                    everyMap.get("time"));
            fileChangeDatas.add(fileChangeData);
        }
        return fileChangeDatas;
    }

    @Override
    public Callback batchPublish(ActionChain chain, NebulaPublishEvent event,HttpServletRequest request, HttpServletResponse response){
        String actionGroupName = event.getPublishActionGroup().getDescription();
        try {
            /** 为null，从未发布 */
            if(event.getNowBatchTag()==null){
                event.setNowBatchTag(1);
                event.setIsBatchFinish(false);
                updateByIdSelective(event);
            /** 所有批次已经发布完 */
            }else if(event.getIsBatchFinish()){
                event.setNowBatchTag(1);
                event.setIsBatchFinish(false);
                updateByIdSelective(event);
            }

            /** 按批次发布 */
            for(int i = event.getNowBatchTag();i<=event.getBatchTotal();i++){
                /** 设置当前执行的批次 */
                event.setNowBatchTag(i);
                updateByIdSelective(event);

                Dispatcher dispatcher = new Dispatcher(chain, request, response);
                Boolean result = dispatcher.doDispatch(event);

                /** 执行结果判断 */
                if(!result){
                    return new Callback("Error", actionGroupName+"出错，发布批次:"+i);
                }
            }
        } catch (Exception e) {
            logger.error("batchPublish error:", e);
            return new Callback("Error", actionGroupName+"出现错误");
        }

        event.setIsBatchFinish(true);
        updateByIdSelective(event);
        return new Callback("Success", actionGroupName+"完成");
    }

}
