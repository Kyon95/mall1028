package com.geekaca.mall.exceptions;

public class LoginFailException extends RuntimeException{
    private Integer code;

    public LoginFailException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
