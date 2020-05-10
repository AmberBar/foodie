package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.bo.SubmitOrderBO;
import com.amber.foodie.pojo.vo.OrderVO;

public interface OrderService {

    /**
     * 创建订单
     *
     * @param submitOrderBO
     */
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 更新订单状态
     *
     * @param merchantOrderId
     * @param type
     */
    void updateOrderStatus(String merchantOrderId, Integer type);

    /**
     * 通知系统订单已经支付
     *
     * @param merchantOrderId
     * @return
     */
    void notifyMerchantOrderPaid(String merchantOrderId);

    /**
     * 关闭超时任务订单
     */
    void closeOrder();

}
