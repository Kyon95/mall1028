package com.geekaca.mall.utils;

import lombok.Data;

import java.util.List;

@Data
public class OrderPayLoad {
    private Long addressId;

    private List<Integer> cartItemIds;

    // getters and setters

}
