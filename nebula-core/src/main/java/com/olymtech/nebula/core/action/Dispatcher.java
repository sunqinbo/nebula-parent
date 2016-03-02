/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action;

import com.olymtech.nebula.core.action.exception.ActionNullException;
import com.olymtech.nebula.core.salt.core.SaltNullTargesException;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishHost;
import com.olymtech.nebula.entity.NebulaPublishModule;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * action核心事件处理器,控制着事件的执行流程,接受一个action链作为真正的事件执行者,\
 * 支持传入web容器的HttpServletRequest与HttpServletResponse,进行web相关操作
 *
 * @author taoshanchang 15/11/4
 */
public class Dispatcher implements Observer {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    private ActionChain actionChain = null;

    private HttpServletRequest request = null;

    private HttpServletResponse response = null;

    private int actionIndex = 0;

    /**
     * 含有HttpServletRequest和HttpServletResponse的构造器,必须传入一个action链
     *
     * @param actionChain
     * @param request
     * @param response
     */
    public Dispatcher(ActionChain actionChain, HttpServletRequest request, HttpServletResponse response) {
        this.actionChain = actionChain;
        this.request = request;
        this.response = response;
    }

    /**
     * 构造器,必须传入一个action链
     *
     * @param actionChain
     */
    public Dispatcher(ActionChain actionChain) {
        this.actionChain = actionChain;
    }

    /**
     * 拦截器,接受一个事件,并按顺序执行链中action的doAction方法
     *
     * @param event 事件
     * @throws Exception
     */
    public void doDispatch(NebulaPublishEvent event) throws Exception {
        List<NebulaPublishModule> publishModules = event.getPublishModules();
        if (publishModules == null || publishModules.size() == 0) {
            throw new SaltNullTargesException("publishModules is null");
        }
        for (NebulaPublishModule publishModule : publishModules) {
            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            if (publishHosts == null || 0 == publishHosts.size()) {
                throw new SaltNullTargesException(publishModule.getPublishModuleName() + "'s publishHosts is null");
            }
        }

        List<Action> actions = actionChain.getActions();
        if (actions != null || actions.size() == 0) {
            for (int i = 0; i < actions.size(); i++) {
                Action action = actions.get(i);
                action.setObserver(this);
                boolean result = false;
                boolean resultCheck = false;
                try {
                    logger.info(action.getActionName() + "开始执行..." + "在链中的id为" + i);
                    result = action.doAction(event);

                    /** action 执行失败 */
                    if (!result) {
                        logger.info(action.getActionName() + "执行失败");
                        triggerFailure(event);
                        return;
                    }

                    /** action 执行成功，但是执行check失败 */
                    resultCheck = action.doCheck(event);
                    if (!resultCheck) {
                        logger.info(action.getActionName() + "执行Check失败");
                        triggerFailure(event);
                        return;
                    }

                    logger.info(action.getActionName() + "执行成功");
                } catch (Exception e) {
                    throw e;
                }
                actionIndex = i;
            }
        } else {
            throw new ActionNullException("动作为空");
        }
    }

    /**
     * dispatcher 执行拦截器链(actionChain)的时候发生错误,会倒序执行链中被执行过的action中的doFailure方法
     *
     * @param event 执行事件
     */
    public void triggerFailure(NebulaPublishEvent event) {
        List<Action> actions = actionChain.getActions();
        try {
            for (int i = actionIndex; i >= 0; i--) {
                logger.info(actions.get(i).getActionName() + "执行回滚中..." + "当前链中action id为" + i);
                actions.get(i).doFailure(event);
                logger.info(actions.get(i).getActionName() + "执行回滚完成");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    @Override
    public void update(String state) {
        System.out.println(state);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public ActionChain getActionChain() {
        return actionChain;
    }

    public void setActionChain(ActionChain actionChain) {
        this.actionChain = actionChain;
    }


}
