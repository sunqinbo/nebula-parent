/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action;

import com.olymtech.nebula.core.action.exception.ActionException;
import com.olymtech.nebula.core.action.exception.ActionNullException;
import com.olymtech.nebula.core.salt.core.SaltNullTargesException;
import com.olymtech.nebula.entity.NebulaPublishEvent;
import com.olymtech.nebula.entity.NebulaPublishHost;
import com.olymtech.nebula.entity.NebulaPublishModule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author taoshanchang 15/11/4
 */
public class Dispatcher implements Observer {

    private ActionChain actionChain = null;

    private HttpServletRequest request = null;

    private HttpServletResponse response = null;

    private int actionIndex = 0;

    public Dispatcher(ActionChain actionChain, HttpServletRequest request, HttpServletResponse response) {
        this.actionChain = actionChain;
        this.request = request;
        this.response = response;
    }


    public void doDispatch(NebulaPublishEvent event) throws ActionException, SaltNullTargesException, ActionNullException {
        List<NebulaPublishModule> publishModules = event.getPublishModules();
        for (NebulaPublishModule publishModule : publishModules) {
            List<NebulaPublishHost> publishHosts = publishModule.getPublishHosts();
            if (publishHosts == null || 0 == publishHosts.size() ) {
                throw new SaltNullTargesException(publishModule.getPublishModuleName() + "'s publishHosts is null");
            }
        }

        List<Action> actions = actionChain.getActions();
        if (actions != null || actions.size() == 0) {
            for (int i = 0; i < actions.size(); i++) {
                Action action = actions.get(i);
                action.setObserver(this);

                boolean result = false;
                try {
                    result = action.doAction(event);
                    if (!result) {
                        triggerFailure(event);
                        return;
                    }
                } catch (Exception e) {
                    if (e instanceof ActionException){
                        throw new ActionException(action.getActionName(),e.getMessage());
                    }else if(e instanceof ActionException) {
                        throw new SaltNullTargesException(e.getMessage());
                    }
                }
                actionIndex = i;
            }
        } else {

            throw new ActionNullException("动作为空");
        }

    }

    public void triggerFailure(NebulaPublishEvent event) {
        List<Action> actions = actionChain.getActions();
        try {
            for (int i = actionIndex; i >= 0; i--) {
                actions.get(i).doFailure(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
