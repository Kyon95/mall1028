package com.geekaca.mall.exceptions;

//自定义异常，未登录异常
public class NotLoginException extends RuntimeException {
    private int code;

    public NotLoginException(int code, String message) {
        super(message);
        this.code = code;

    }
}