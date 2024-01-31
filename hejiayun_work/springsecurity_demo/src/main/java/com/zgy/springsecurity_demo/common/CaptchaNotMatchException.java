package com.zgy.springsecurity_demo.common;

import org.springframework.security.core.AuthenticationException;

public class CaptchaNotMatchException extends AuthenticationException {

    public CaptchaNotMatchException(String msg) {
        super(msg);
    }

    public CaptchaNotMatchException(String msg, Throwable cause) {
        super(msg, cause);
    }
}