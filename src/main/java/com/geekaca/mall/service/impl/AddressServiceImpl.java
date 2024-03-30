package com.geekaca.mall.service.impl;

import com.geekaca.mall.domain.UserAddress;
import com.geekaca.mall.mapper.UserAddressMapper;
import com.geekaca.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> getAllAddressByUserId(Long userId) {
        List<UserAddress> addressList = userAddressMapper.selectAllAddressByUid(userId);
        if (addressList == null) {
            return Collections.emptyList();
        }
        return addressList;
    }


}
