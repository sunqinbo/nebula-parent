/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.olymtech.nebula.web.exception;

import org.apache.shiro.authc.AccountException;

/**
 * Created by Gavin on 2016-02-26 14:39.
 */
public class GoogleAuthAccountException extends AccountException {

    public GoogleAuthAccountException() {
    }

    public GoogleAuthAccountException(String message) {
        super(message);
    }

    public GoogleAuthAccountException(Throwable cause) {
        super(cause);
    }

    public GoogleAuthAccountException(String message, Throwable cause) {
        super(message, cause);
    }

}
