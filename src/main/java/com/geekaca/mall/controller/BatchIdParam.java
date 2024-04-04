package com.geekaca.mall.controller;

import lombok.Data;

/**
 * 该参数  在以下场景也会用到
 * 前台用户  【确认收货、取消订单】
 * 后台管理员  【配货、出库】
 *
 * 成员属性ids可满足基本需求
 * 可按照自己需求  往该类添加成员属性
 */
@Data
public class BatchIdParam {
    /**
     * id数组
     */
    private Long[] ids;
}
