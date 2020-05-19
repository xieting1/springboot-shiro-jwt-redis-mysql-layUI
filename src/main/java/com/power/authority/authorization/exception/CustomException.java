package com.power.authority.authorization.exception;

/**
 * @program: authorization
 * @description: 系统异常类
 * @author: xie ting
 * @create: 2020-05-11 17:50
 */
public class CustomException extends RuntimeException {

    public CustomException(String exception) {
        super(exception);
    }
}
