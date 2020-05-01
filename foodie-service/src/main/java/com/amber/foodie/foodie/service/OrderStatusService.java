package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.OrderStatus;
import com.amber.foodie.pojo.bo.SubmitOrderBO;

public interface OrderStatusService {

    /**
     * 创建订单
     * @param orderStatus
     */
    public void create(OrderStatus orderStatus);
}
