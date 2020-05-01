package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.OrderItemService;
import com.amber.foodie.mapper.OrderItemsMapper;
import com.amber.foodie.pojo.OrderItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    OrderItemsMapper orderItemsMapper;

    /**
     * 创建订单明细
     * @param orderItems
     */
    @Override
    public void createOrderItem(OrderItems orderItems) {
        orderItemsMapper.insert(orderItems);
    }
}
