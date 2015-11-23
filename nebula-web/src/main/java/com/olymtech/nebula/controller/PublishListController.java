package com.olymtech.nebula.controller;

import com.github.pagehelper.PageInfo;
import com.olymtech.nebula.entity.DataTablePage;
import com.olymtech.nebula.service.IPublishEventService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by liwenji on 2015/11/6.
 */
@Controller
@RequestMapping("/publish_event")
public class PublishListController extends BaseController {
    @Resource
    HttpServletRequest request;

    @Resource
    IPublishEventService publishEventService;

    @RequestMapping(value = {"/publishList"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object PublishList(DataTablePage dataTablePage) {
        PageInfo pageInfo = publishEventService.getPublishEvent(dataTablePage);
        return pageInfo;
    }
}
