/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action;

import com.olymtech.nebula.core.action.exception.ActionException;
import com.olymtech.nebula.entity.NebulaPublishEvent;

/**
 * @author taoshanchang 15/11/4
 */
public class Action1 extends AbstractAction {

    public Action1(String actionName) {
        super();
    }

    @Override
    public boolean doAction(NebulaPublishEvent event) throws ActionException {

        try {
            System.out.println("创建文件夹");
            //int a = 12/0;
            this.nodifyObservers("创建文件夹动作执行完成");
        }catch (Exception e){

            System.out.println("Action1 Exception");
            throw new ActionException(this.getActionName(),"xxx");

        }
        return true;
    }

    @Override
    public void doFailure(NebulaPublishEvent event) {
        System.out.println("回滚创建文件夹");
    }
}
