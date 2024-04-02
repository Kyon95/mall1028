package com.geekaca.mall.service.impl;

import com.geekaca.mall.domain.OrderAddress;
import com.geekaca.mall.mapper.OrderAddressMapper;
import com.geekaca.mall.service.OrderAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderAddressServiceImpl implements OrderAddressService {
    @Autowired
    OrderAddressMapper orderAddressMapper;

    @Override
    public int insertOrderAddress(OrderAddress orderAddress) {
        return orderAddressMapper.insertSelective(orderAddress);
    }
}
