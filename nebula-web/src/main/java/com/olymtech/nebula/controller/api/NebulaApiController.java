package com.olymtech.nebula.controller.api;

import com.olymtech.nebula.common.utils.DateUtils;
import com.olymtech.nebula.controller.BaseController;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaUserInfo;
import com.olymtech.nebula.entity.enums.LogAction;
import com.olymtech.nebula.entity.enums.PublishStatus;
import com.olymtech.nebula.service.IUserService;
import com.olymtech.nebula.service.impl.PublishEventLogServiceImpl;
import com.olymtech.nebula.service.impl.PublishEventServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangyiqiang on 16/5/6.
 */
@Controller
@RequestMapping("/nebulaApi")
public class NebulaApiController extends BaseController {

    @Resource
    private PublishEventServiceImpl publishEventService;

    @Resource
    private PublishEventLogServiceImpl publishEventLogService;

    @Resource
    private IUserService userService;

    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object createPublishEvent(NebulaPublishEvent nebulaPublishEvent) {
        String publishSvn = nebulaPublishEvent.getPublishSvn();

        String patternOnline = "svn://svn.olymtech.com/warspace/";
        Pattern pO = Pattern.compile(patternOnline);
        Matcher matchO = pO.matcher(publishSvn);

        String patternTest="172.16.137.150";
        Pattern pT = Pattern.compile(patternTest);
        Matcher matchT = pT.matcher(publishSvn);

        if (!(matchO.find() || matchT.find()) ) {
            return returnCallback("Error", "请检测svn地址（svn://svn.olymtech.com/warspace/）");
        }
        nebulaPublishEvent.setPublishStatus(PublishStatus.PENDING_APPROVE);
        NebulaUserInfo user = userService.findByUsername("quarryWeb");

        String publishEnv = nebulaPublishEvent.getPublishEnv();
        String patternEnv = "test";
        Pattern pEnv = Pattern.compile(patternEnv);
        Matcher matchEnv = pEnv.matcher(publishEnv);
        if(matchEnv.find()){
            nebulaPublishEvent.setPublishStatus(PublishStatus.PENDING_PRE);
            nebulaPublishEvent.setIsApproved(true);
            nebulaPublishEvent.setApproveEmpId(nebulaPublishEvent.getSubmitEmpId());
            nebulaPublishEvent.setApproveDatetime(DateUtils.addSeconds(new Date(), 2));
        }

        int id = publishEventService.createPublishEvent(nebulaPublishEvent);
        publishEventLogService.logPublishAction(id, LogAction.APPLY_PUBLISH_EVENT,user.getNickname() + "申请创建发布事件成功!", user.getEmpId());
        return returnCallback("Success", id);
    }

}
