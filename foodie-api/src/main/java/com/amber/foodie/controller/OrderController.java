package com.amber.foodie.controller;

import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.foodie.service.OrderService;
import com.amber.foodie.foodie.service.PaymentService;
import com.amber.foodie.pojo.bo.SubmitOrderBO;
import com.amber.foodie.pojo.enums.OrderStatusEnum;
import com.amber.foodie.pojo.enums.PayMethod;
import com.amber.foodie.pojo.vo.MerchantOrdersVO;
import com.amber.foodie.pojo.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Api(value = "订单管理", tags = {"订单管理api接口"})
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    PaymentService paymentService;

    /**
     * 创建订单:
     * 1. 创建订单
     * 2. 创建订单完成后，把购物车内相关的数据删除
     * 3. 向交易中心发送当前订单
     *
     * @param submitOrderBO
     * @return
     */
    @ApiOperation(value = "创建订单", notes = "创建订单", httpMethod = "POST")
    @PostMapping("/create")
    public JsonResult add(
            @ApiParam(name = "submitOrderBO", value = "提交订单数据", required = true)
            @RequestBody SubmitOrderBO submitOrderBO
    ) throws Exception {
        if (submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type && submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type) {
            return JsonResult.errorMsg("支付方式不支持");
        }
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String payReturnUrl = "http://hmhsb3.natappfree.cc/payment/notice/wxpay";
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);
        // 所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);
        String orderId = orderVO.getOrderId();

        paymentService.createMerchantOrder(merchantOrdersVO);

        return JsonResult.ok(orderId);
    }

    /**
     * 通知支付完成,更新状态
     *
     * @param merchantOrderId
     * @return
     */
    @GetMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }
}
