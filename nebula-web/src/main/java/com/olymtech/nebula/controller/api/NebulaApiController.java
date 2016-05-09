package com.olymtech.nebula.controller.api;

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

    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Object createPublishEvent(NebulaPublishEvent nebulaPublishEvent) {
        String publishSvn = nebulaPublishEvent.getPublishSvn();
        String pattern = "svn://svn.olymtech.com/warspace/";
        Pattern p = Pattern.compile(pattern);
        Matcher match = p.matcher(publishSvn);
        if (!match.find()) {
            return returnCallback("Error", "请检测svn地址（svn://svn.olymtech.com/warspace/）");
        }
        nebulaPublishEvent.setPublishStatus(PublishStatus.PENDING_APPROVE);
        NebulaUserInfo user = userService.findByUsername("quarryWeb");

        int id = publishEventService.createPublishEvent(nebulaPublishEvent);
        publishEventLogService.logPublishAction(id, LogAction.APPLY_PUBLISH_EVENT,user.getNickname() + "申请创建发布事件成功!", user.getEmpId());
        return returnCallback("Success", id);
    }

}
