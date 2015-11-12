/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved
 */
package com.olymtech.nebula.core.salt.core;

import com.suse.saltstack.netapi.exception.SaltStackException;

/**
 * Exception to be thrown in case of problems with SaltStack.
 * @author taoshanchang 15/11/12
 */
public class SaltNullTargesException extends SaltStackException {


    /**
     * Constructor expecting a custom cause.
     * @param cause the cause
     */
    public SaltNullTargesException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor expecting a custom message.
     * @param message the message
     */
    public SaltNullTargesException(String message) {
        super(message);
    }

    /**
     * Constructor expecting a custom message.
     */
    public SaltNullTargesException() {
        super("minion is not allowed be null");
    }
}
