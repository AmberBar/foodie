package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.User;
import com.amber.foodie.pojo.bo.SubmitOrderBO;
import com.amber.foodie.pojo.bo.UserBO;

public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderBO
     */
    public void createOrder(SubmitOrderBO submitOrderBO);
}
