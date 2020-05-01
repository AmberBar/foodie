package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.AddressService;
import com.amber.foodie.mapper.UserAddressMapper;
import com.amber.foodie.pojo.UserAddress;
import com.amber.foodie.pojo.bo.AddressBO;
import com.amber.foodie.pojo.enums.YesOrNo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserAddressMapper userAddressMapper;

    /**
     * 新增收货地址
     *
     * @param addressBO
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void create(AddressBO addressBO) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(addressBO.getUserId());
        int i = userAddressMapper.selectCount(userAddress);
        Integer isDefault = 0;
        if (i == 0) {
            isDefault = 1;
        }
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(UUID.randomUUID().toString());
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(userAddress);
    }

    /**
     * 根据用户id查找所有地址
     *
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAllAddress(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteAddressById(String id) {
        userAddressMapper.deleteByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(AddressBO addressBO) {
        String addressId = addressBO.getAddressId();
        UserAddress updateAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, updateAddress);
        updateAddress.setUpdatedTime(new Date());
        updateAddress.setId(addressId);
        userAddressMapper.updateByPrimaryKeySelective(updateAddress);
    }

    /**
     * 设置默认地址
     *
     * @param userId
     * @param addtessId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void defaultAddress(String userId, String addtessId) {
        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("isDefault", YesOrNo.Yes.type);
        UserAddress userAddressOld = userAddressMapper.selectOneByExample(example);
        if (userAddressOld != null) {
            userAddressOld.setIsDefault(YesOrNo.No.type);
            userAddressMapper.updateByPrimaryKey(userAddressOld);
        }
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addtessId);
        if (userAddress == null) {
            return;
        }
        userAddress.setIsDefault(YesOrNo.Yes.type);
        userAddressMapper.updateByPrimaryKey(userAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress findUserAddressById(String addressId) {
        return userAddressMapper.selectByPrimaryKey(addressId);
    }
}
