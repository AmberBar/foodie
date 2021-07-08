package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.OrderStatus;
import com.amber.foodie.pojo.bo.SubmitOrderBO;

import java.util.List;

public interface OrderStatusService {

    /**
     * 创建订单
     * @param orderStatus
     */
    public void create(OrderStatus orderStatus);

    /**
     * 更新订单状态
     * @param status
     */
    void update(OrderStatus status);

    /**
     * 通过状态查询订单状态
     * @return
     */
    List<OrderStatus> findAllByStatus(Integer type);
}
