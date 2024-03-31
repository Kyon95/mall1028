package com.geekaca.mall.utils;

import org.springframework.beans.BeanUtils;

public class BeanUtil {
    public static Object copyProperties(Object source, Object target, String... ignoreProperties) {
        if (source == null) {
            return target;
        } else {
            BeanUtils.copyProperties(source, target, ignoreProperties);
            return target;
        }
    }
}
