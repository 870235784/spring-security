package com.tca.security.core.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zhoua
 * @Date 2020/3/26
 * 图形验证码校验异常
 */
public class ImageCodeException extends AuthenticationException {

    public ImageCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ImageCodeException(String msg) {
        super(msg);
    }
}
