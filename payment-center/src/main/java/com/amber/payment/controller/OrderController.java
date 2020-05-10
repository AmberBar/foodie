package com.amber.payment.controller;

import com.amber.payment.enums.PayMethod;
import com.amber.payment.pojo.JsonResult;
import com.amber.payment.pojo.vo.MerchantOrdersVO;
import com.amber.payment.service.PaymentOrderService;
import com.amber.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    PaymentService paymentService;

    /**
     * 接收订单
     * @param merchantOrdersVO
     * @return
     * @throws Exception
     */
    @PostMapping("/create")
    public JsonResult createMerchantOrder(@RequestBody MerchantOrdersVO merchantOrdersVO) throws Exception {
        JsonResult merchantOrder = paymentService.createMerchantOrder(merchantOrdersVO);
        return merchantOrder;
    }
}
