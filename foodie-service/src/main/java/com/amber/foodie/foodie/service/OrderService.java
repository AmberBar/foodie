package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.bo.SubmitOrderBO;
import com.amber.foodie.pojo.enums.OrderStatusEnum;
import com.amber.foodie.pojo.vo.OrderVO;
import org.springframework.http.HttpStatus;

public interface OrderService {

    /**
     * 创建订单
     * @param submitOrderBO
     */
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 更新订单状态
     * @param merchantOrderId
     * @param type
     */
    void updateOrderStatus(String merchantOrderId, Integer type);

    /**
     * 通知系统订单已经支付
     * @param merchantOrderId
     * @return
     */
    void notifyMerchantOrderPaid(String merchantOrderId);

}
