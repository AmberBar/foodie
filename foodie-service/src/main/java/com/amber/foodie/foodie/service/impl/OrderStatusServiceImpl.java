package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.OrderStatusService;
import com.amber.foodie.mapper.OrderStatusMapper;
import com.amber.foodie.pojo.OrderStatus;
import com.amber.foodie.pojo.enums.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    @Autowired
    OrderStatusMapper orderStatusMapper;

    @Override
    public void create(OrderStatus orderStatus) {
        orderStatusMapper.insert(orderStatus);
    }

    @Override
    public void update(OrderStatus orderStatus) {
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Override
    public List<OrderStatus> findAllByStatus(Integer type) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(type);
        return orderStatusMapper.select(orderStatus);
    }
}
