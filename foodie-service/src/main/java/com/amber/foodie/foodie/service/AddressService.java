package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.UserAddress;
import com.amber.foodie.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {
    /**
     * 添加收货地址
     *
     * @param addressBO
     */
    void create(AddressBO addressBO);

    /**
     * 根据用户id查询所有收获地址
     *
     * @return
     */
    List<UserAddress> queryAllAddress(String userId);

    /**
     * 删除收货地址
     *
     * @param id
     */
    void deleteAddressById(String id);

    /**
     * 更新收货地址
     *
     * @param addressBO
     */
    public void update(AddressBO addressBO);

    /**
     * 设置默认地址
     */
    public void defaultAddress(String userId, String id);
}
