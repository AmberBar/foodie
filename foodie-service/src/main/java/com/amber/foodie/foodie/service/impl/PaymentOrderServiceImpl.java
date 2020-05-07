package com.amber.foodie.foodie.service.impl;


import com.amber.foodie.foodie.service.PaymentOrderService;
import com.amber.foodie.mapper.TradingCenterOrderMapper;
import com.amber.foodie.pojo.PaymentOrder;
import com.amber.foodie.pojo.enums.PaymentStatus;
import com.amber.foodie.pojo.enums.YesOrNo;
import com.amber.foodie.pojo.vo.MerchantOrdersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.UUID;

@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Autowired
    private TradingCenterOrderMapper tradingCenterOrderMapper;

    /**
     * 创建订单
     * @param merchantOrdersBO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean createPaymentOrder(MerchantOrdersVO merchantOrdersBO) {
        String id = UUID.randomUUID().toString();
        PaymentOrder paymentOrder = new PaymentOrder();
        BeanUtils.copyProperties(merchantOrdersBO, paymentOrder);
        paymentOrder.setId(id);
        paymentOrder.setPayStatus(PaymentStatus.WAIT_PAY.type);
        paymentOrder.setComeFrom("Amber网站");
        paymentOrder.setIsDelete(YesOrNo.No.type);
        paymentOrder.setCreatedTime(new Date());

        int row = tradingCenterOrderMapper.insert(paymentOrder);
        if (row == 1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PaymentOrder queryOrderByStatus(String merchantUserId, String merchantOrderId, Integer orderStatus) {
        Example example = new Example(PaymentOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("merchant_order_id", merchantOrderId);
        criteria.andEqualTo("merchant_user_id", merchantUserId);
        criteria.andEqualTo("pay_status", orderStatus);
        PaymentOrder paymentOrder = tradingCenterOrderMapper.selectOneByExample(example);
        return paymentOrder;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String updateOrderPaid(String merchantOrderId, Integer paidAmount) {
        Example example = new Example(PaymentOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("merchant_order_id", merchantOrderId);
        PaymentOrder paymentOrder = tradingCenterOrderMapper.selectOneByExample(example);
        paymentOrder.setPayStatus(PaymentStatus.PAID.type);
        paymentOrder.setAmount(paidAmount);
        tradingCenterOrderMapper.updateByPrimaryKey(paymentOrder);
        return queryMerchantReturnUrl(merchantOrderId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    String queryMerchantReturnUrl(String merchantOrderId) {
        Example example = new Example(PaymentOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("merchant_order_id", merchantOrderId);
        PaymentOrder paymentOrder = tradingCenterOrderMapper.selectOneByExample(example);
        return paymentOrder.getReturnUrl();
    }

    @Override
    public PaymentOrder queryOrderInfo(String merchantUserId, String merchantOrderId) {
        Example example = new Example(PaymentOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("merchant_order_id", merchantOrderId);
        criteria.andEqualTo("merchant_user_id", merchantUserId);
        PaymentOrder paymentOrder = tradingCenterOrderMapper.selectOneByExample(example);
        return paymentOrder;
    }
}
