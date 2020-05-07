package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.bo.SubmitOrderBO;
import com.amber.foodie.pojo.vo.OrderVO;

public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderBO
     */
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    void updateOrderStatus(String merchantOrderId, Integer type);
}
