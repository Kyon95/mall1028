package com.geekaca.mall.service.impl;

import com.geekaca.mall.domain.UserAddress;
import com.geekaca.mall.mapper.UserAddressMapper;
import com.geekaca.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.geekaca.mall.common.MallConstants.ADDRESS_NOT_DEFAULT;

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

    //若新增的是默认地址且已有默认地址，就既要insert也要update，两者要捆绑在同一事务内
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addAddress(UserAddress address) {
        //若新增地址没设默认，直接执行insert语句
        if (address.getDefaultFlag() == ADDRESS_NOT_DEFAULT) {
            return userAddressMapper.insert(address);
        }

        //走到该处，证明新增地址设了默认  则查看已存地址中是否有默认地址
        UserAddress userAdd = userAddressMapper.selectDefaultAddressByUid(address.getUserId());
        if (userAdd == null) {
            return userAddressMapper.insert(address);//没有，则执行insert语句
        }

        //走到该处，证明已存地址中有默认地址，则将该默认地址改为非默认，再执行insert语句
        userAdd.setDefaultFlag(ADDRESS_NOT_DEFAULT);
        userAdd.setUpdateTime(new Date());//改一下update_time
        int updated = userAddressMapper.updateByPrimaryKeySelective(userAdd);
        if (updated <= 0) {
            return 0;//若更改失败，则返回0让对应controller方法也失败
        } else {// >0 即更改成功，执行insert语句
            return userAddressMapper.insert(address);
        }
    }

    @Override
    public UserAddress getAddressByAddressId(Long addressId) {
        return userAddressMapper.selectByPrimaryKey(addressId);
    }

    //若要改某地址为默认地址，且已存在默认地址，则执行两个update语句，需捆绑
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int setAddress(UserAddress address) {
        //若被修改地址没设默认，直接执行update语句
        if (address.getDefaultFlag() == ADDRESS_NOT_DEFAULT) {
            return userAddressMapper.updateByPrimaryKeySelective(address);
        }
        //走到该处，证明被修改地址设了默认  则查看已存地址中是否有默认地址 //该处的user_id需controller内补上↓↓
        UserAddress userAdd = userAddressMapper.selectDefaultAddressByUid(address.getUserId());
        if (userAdd == null) {
            return userAddressMapper.updateByPrimaryKeySelective(address);//没有，则执行update语句
        }
        //走到该处，证明已存在默认地址，则将该默认地址改为非默认，再执行update语句
        userAdd.setDefaultFlag(ADDRESS_NOT_DEFAULT);
        userAdd.setUpdateTime(new Date());//改一下update_time
        int updated = userAddressMapper.updateByPrimaryKeySelective(userAdd);
        if (updated <= 0) {
            return 0;//若更改失败，则返回0让对应controller方法也失败
        } else {// >0 即更改成功，执行update语句
            address.setUpdateTime(new Date());//这边也改一下update_time
            return userAddressMapper.updateByPrimaryKeySelective(address);
        }
    }

    @Override
    public int deleteAddress(Long addressId, Long userId) {
        return userAddressMapper.deleteByPrimaryKey(addressId, userId);
    }


}
