package com.geekaca.mall.exceptions;

/**
 * 通用化的 业务逻辑异常
 */
public class MallException extends RuntimeException {
    public MallException(String msg) {
        super(msg);
    }
}
