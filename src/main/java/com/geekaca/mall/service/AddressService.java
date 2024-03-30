package com.geekaca.mall.service;

import com.geekaca.mall.domain.UserAddress;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AddressService {
    List<UserAddress> getAllAddressByUserId(Long userId);

    int addAddress(UserAddress address);

    UserAddress getAddressByAddressId(Long addressId);
}
