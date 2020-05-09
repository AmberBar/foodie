package com.amber.foodie.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.common.utils.RedisUtils;
import com.amber.foodie.common.wx.entity.PreOrderResult;
import com.amber.foodie.common.wx.resource.AliPayResource;
import com.amber.foodie.common.wx.resource.WXPayResource;
import com.amber.foodie.common.wx.service.WxOrderService;
import com.amber.foodie.foodie.service.PaymentOrderService;
import com.amber.foodie.pojo.PaymentOrder;
import com.amber.foodie.pojo.enums.PaymentStatus;
import com.amber.foodie.pojo.vo.PaymentInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PayController {

    @Autowired
    public RedisUtils redis;

    @Autowired
    private WXPayResource wxPayResource;
    @Autowired
    private AliPayResource aliPayResource;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private WxOrderService wxOrderService;

    /******************************************  以下所有方法开始支付流程   ******************************************/

    /**
     * @Description: 微信扫码支付页面
     */
    @PostMapping(value = "/getWXPayQRCode")
    public JsonResult getWXPayQRCode(String merchantOrderId, String merchantUserId) throws Exception {

        // 根据订单ID和用户ID查询订单详情
        PaymentOrder waitPayOrder = paymentOrderService.queryOrderByStatus(merchantUserId,
                merchantOrderId, PaymentStatus.WAIT_PAY.type);

        // 商品描述
        String body = "天天吃货-付款用户[" + merchantUserId + "]";
        // 商户订单号
        String out_trade_no = merchantOrderId;
        // 从redis中去获得这笔订单的微信支付二维码，如果订单状态没有支付没有就放入，这样的做法防止用户频繁刷新而调用微信接口
        if (waitPayOrder != null) {
            String qrCodeUrl = redis.get(wxPayResource.getQrcodeKey() + ":" + merchantOrderId);

            if (StringUtils.isEmpty(qrCodeUrl)) {
                // 订单总金额，单位为分
                String total_fee = String.valueOf(waitPayOrder.getAmount());
//				String total_fee = "1";	// 测试用 1分钱

                // 统一下单
                PreOrderResult preOrderResult = wxOrderService.placeOrder(body, out_trade_no, total_fee);
                qrCodeUrl = preOrderResult.getCode_url();
            }

            PaymentInfoVO paymentInfoVO = new PaymentInfoVO();
            paymentInfoVO.setAmount(waitPayOrder.getAmount());
            paymentInfoVO.setMerchantOrderId(merchantOrderId);
            paymentInfoVO.setMerchantUserId(merchantUserId);
            paymentInfoVO.setQrCodeUrl(qrCodeUrl);


            return JsonResult.ok(paymentInfoVO);
        } else {
            return JsonResult.errorMsg("该订单不存在，或已经支付");
        }
    }


    /**
     * @return
     * @throws Exception
     * @Description: 前往支付宝进行支付
     */
    public JsonResult goAlipay(String merchantOrderId, String merchantUserId) throws Exception {

        // 查询订单详情
        PaymentOrder waitPayOrder = paymentOrderService.queryOrderByStatus(merchantUserId, merchantOrderId, PaymentStatus.WAIT_PAY.type);

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayResource.getGatewayUrl(),
                aliPayResource.getAppId(),
                aliPayResource.getMerchantPrivateKey(),
                "json",
                aliPayResource.getCharset(),
                aliPayResource.getAlipayPublicKey(),
                aliPayResource.getSignType());

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(aliPayResource.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayResource.getNotifyUrl());

        // 商户订单号, 商户网站订单系统中唯一订单号, 必填
        String out_trade_no = merchantOrderId;
        // 付款金额, 必填 单位元
        //String total_amount = CurrencyUtils.getFen2YuanWithPoint(waitPayOrder.getAmount());
        String total_amount = "0.01";    // 测试用 1分钱
        // 订单名称, 必填
        String subject = "天天吃货-付款用户[" + merchantUserId + "]";
        // 商品描述, 可空, 目前先用订单名称
        String body = subject;

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1d";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\"" + timeout_express + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数, 以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String alipayForm = "";
        try {
            alipayForm = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }


        return JsonResult.ok(alipayForm);
    }

}
