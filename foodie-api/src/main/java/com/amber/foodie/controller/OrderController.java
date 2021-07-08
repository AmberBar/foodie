package com.amber.foodie.controller;

import com.amber.foodie.common.constant.Constant;
import com.amber.foodie.common.utils.CookieUtils;
import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.common.utils.RedisUtils;
import com.amber.foodie.foodie.service.OrderService;
import com.amber.foodie.pojo.bo.SubmitOrderBO;
import com.amber.foodie.pojo.enums.OrderStatusEnum;
import com.amber.foodie.pojo.enums.PayMethod;
import com.amber.foodie.pojo.vo.MerchantOrdersVO;
import com.amber.foodie.pojo.vo.OrderVO;
import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/orders")
@Api(value = "订单管理", tags = {"订单管理api接口"})
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${payment.url}")
    String paymentUrl;

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
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {
        if (submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type && submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type) {
            return JsonResult.errorMsg("支付方式不支持");
        }

        // 本地创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String payReturnUrl = paymentUrl + "/orders/create";

        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        // TODO
        merchantOrdersVO.setReturnUrl("http://localhost:8088/orders/notifyMerchantOrderPaid");

        // 所有的支付金额都统一改为1分钱
        merchantOrdersVO.setAmount(1);
        String orderId = orderVO.getOrderId();

        //整合redis，完善购物车中已结算商品清除，并且同步到前端cookie

        CookieUtils.setCookie(request, response, Constant.FOOID_SHOPCART, "");
        // 发送给交易中心
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("appId", "wx8293dbaa6ef46dfa");
        headers.add("appSecret", "cdf0de291eafecc7be34214ee2b5f313");

        HttpEntity<MerchantOrdersVO> requestParams = new HttpEntity<>(merchantOrdersVO, headers);
        ResponseEntity<JsonResult> responseEntity = restTemplate.postForEntity(payReturnUrl, requestParams, JsonResult.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            return JsonResult.errorMsg("支付中心订单创建失败");
        } else {
            return JsonResult.ok(orderId);
        }
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
