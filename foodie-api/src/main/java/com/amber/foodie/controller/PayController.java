package com.amber.foodie.controller;

import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.common.utils.RedisUtils;
import com.amber.foodie.pojo.vo.PaymentInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pay")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PayController {

    @Autowired
    public RedisUtils redisUtils;

    @Autowired
    RestTemplate restTemplate;

    @Value("${payment.url}")
    String paymentUrl;

    /**
     * @Description: 微信扫码支付页面
     */
    @PostMapping(value = "/getWXPayQRCode")
    public JsonResult getWXPayQRCode(String merchantOrderId, String merchantUserId) throws Exception {
        ResponseEntity<PaymentInfoVO> response = restTemplate.postForEntity(
                paymentUrl + "/pay/getWXPayQRCode?merchantUserId={1}&merchantOrderId={2}",
                null,PaymentInfoVO.class, merchantUserId, merchantOrderId);
        PaymentInfoVO body = response.getBody();

        if (response.getStatusCode() != HttpStatus.OK || body == null ) {
            return JsonResult.errorMsg("该订单不存在，或已经支付");
        } else {
            return JsonResult.ok(body);
        }
    }


    /**
     * @return
     * @throws Exception
     * @Description: 前往支付宝进行支付
     */
    public JsonResult goAlipay(String merchantOrderId, String merchantUserId) throws Exception {
//
//        // 查询订单详情
//        PaymentOrder waitPayOrder = paymentOrderService.queryOrderByStatus(merchantUserId, merchantOrderId, PaymentStatus.WAIT_PAY.type);
//
//        //获得初始化的AlipayClient
//        AlipayClient alipayClient = new DefaultAlipayClient(aliPayResource.getGatewayUrl(),
//                aliPayResource.getAppId(),
//                aliPayResource.getMerchantPrivateKey(),
//                "json",
//                aliPayResource.getCharset(),
//                aliPayResource.getAlipayPublicKey(),
//                aliPayResource.getSignType());
//
//        //设置请求参数
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
//        alipayRequest.setReturnUrl(aliPayResource.getReturnUrl());
//        alipayRequest.setNotifyUrl(aliPayResource.getNotifyUrl());
//
//        // 商户订单号, 商户网站订单系统中唯一订单号, 必填
//        String out_trade_no = merchantOrderId;
//        // 付款金额, 必填 单位元
//        //String total_amount = CurrencyUtils.getFen2YuanWithPoint(waitPayOrder.getAmount());
//        String total_amount = "0.01";    // 测试用 1分钱
//        // 订单名称, 必填
//        String subject = "天天吃货-付款用户[" + merchantUserId + "]";
//        // 商品描述, 可空, 目前先用订单名称
//        String body = subject;
//
//        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
//        String timeout_express = "1d";
//
//        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
//                + "\"total_amount\":\"" + total_amount + "\","
//                + "\"subject\":\"" + subject + "\","
//                + "\"body\":\"" + body + "\","
//                + "\"timeout_express\":\"" + timeout_express + "\","
//                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
//
//        //请求
//        String alipayForm = "";
//        try {
//            alipayForm = alipayClient.pageExecute(alipayRequest).getBody();
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//
//
//        return JsonResult.ok(alipayForm);
        return null;
    }

}
