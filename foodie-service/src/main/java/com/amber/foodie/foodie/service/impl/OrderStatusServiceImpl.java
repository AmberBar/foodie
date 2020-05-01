package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.OrderStatusService;
import com.amber.foodie.mapper.OrderStatusMapper;
import com.amber.foodie.pojo.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    @Autowired
    OrderStatusMapper orderStatusMapper;

    @Override
    public void create(OrderStatus orderStatus) {
        orderStatusMapper.insert(orderStatus);
    }
}
