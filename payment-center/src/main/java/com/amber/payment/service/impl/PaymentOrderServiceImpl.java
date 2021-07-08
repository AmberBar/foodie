package com.amber.payment.service.impl;


import com.amber.payment.enums.PaymentStatus;
import com.amber.payment.enums.YesOrNo;
import com.amber.payment.mapper.PaymentOrderMapper;
import com.amber.payment.pojo.PaymentOrder;
import com.amber.payment.pojo.vo.MerchantOrdersVO;
import com.amber.payment.service.PaymentOrderService;
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
    private PaymentOrderMapper paymentOrderMapper;

    /**
     * 创建订单
     *
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

        int row = paymentOrderMapper.insert(paymentOrder);
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
        criteria.andEqualTo("merchantOrderId", merchantOrderId);
        criteria.andEqualTo("merchantUserId", merchantUserId);
        criteria.andEqualTo("payStatus", orderStatus);
        PaymentOrder paymentOrder = paymentOrderMapper.selectOneByExample(example);
        return paymentOrder;
    }

    /**
     * 更改订单状态，并且返回通知url
     *
     * @param merchantOrderId
     * @param paidAmount
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String updateOrderPaid(String merchantOrderId, Integer paidAmount) {
        Example example = new Example(PaymentOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("merchantOrderId", merchantOrderId);
        PaymentOrder paymentOrder = paymentOrderMapper.selectOneByExample(example);
        paymentOrder.setPayStatus(PaymentStatus.PAID.type);
        paymentOrder.setAmount(paidAmount);
        paymentOrderMapper.updateByPrimaryKey(paymentOrder);
        return queryMerchantReturnUrl(merchantOrderId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    String queryMerchantReturnUrl(String merchantOrderId) {
        Example example = new Example(PaymentOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("merchantOrderId", merchantOrderId);
        PaymentOrder paymentOrder = paymentOrderMapper.selectOneByExample(example);
        return paymentOrder.getReturnUrl();
    }

    @Override
    public PaymentOrder queryOrderInfo(String merchantUserId, String merchantOrderId) {
        Example example = new Example(PaymentOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("merchantOrderId", merchantOrderId);
        criteria.andEqualTo("merchantUserId", merchantUserId);
        PaymentOrder paymentOrder = paymentOrderMapper.selectOneByExample(example);
        return paymentOrder;
    }
}
