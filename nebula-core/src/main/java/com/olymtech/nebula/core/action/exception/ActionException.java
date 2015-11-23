/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.action.exception;

/**
 * @author taoshanchang 15/11/23
 */
public class ActionException extends Exception {

    private String actionName;

    public ActionException() {
        super();
    }

    public ActionException(String message) {
        super(message);
    }

    public ActionException(String actionName, String message) {
        super(message);
        this.actionName = actionName;
    }

    public ActionException(Throwable cause) {
        super(cause);
    }

    public ActionException(String message, Throwable cause) {
        super(message, cause);
    }
}
