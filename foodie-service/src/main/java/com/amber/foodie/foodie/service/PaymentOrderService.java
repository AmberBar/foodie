package com.amber.foodie.foodie.service;


import com.amber.foodie.pojo.PaymentOrder;
import com.amber.foodie.pojo.vo.MerchantOrdersVO;

public interface PaymentOrderService {

    /**
     * @Description: 创建支付中心的订单
     */
    boolean createPaymentOrder(MerchantOrdersVO merchantOrdersBO);

    /**
     * @Description: 查询未支付订单
     */
    PaymentOrder queryOrderByStatus(String merchantUserId, String merchantOrderId, Integer orderStatus);

    /**
     * @Description: 修改订单状态为已支付
     */
    String updateOrderPaid(String merchantOrderId, Integer paidAmount);

    /**
     * @Description: 查询订单信息
     */
    PaymentOrder queryOrderInfo(String merchantUserId, String merchantOrderId);
}

