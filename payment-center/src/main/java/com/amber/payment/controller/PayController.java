package com.amber.payment.controller;

import com.alibaba.fastjson.JSONObject;
import com.amber.payment.enums.PaymentStatus;
import com.amber.payment.pojo.JsonResult;
import com.amber.payment.pojo.PaymentOrder;
import com.amber.payment.pojo.PreOrderResult;
import com.amber.payment.pojo.vo.PaymentInfoVO;
import com.amber.payment.resource.AliPayResource;
import com.amber.payment.resource.WXPayResource;
import com.amber.payment.service.PaymentOrderService;
import com.amber.payment.service.WxOrderService;
import com.amber.payment.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pay")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PayController {

    @Autowired
    public RedisUtils redisUtils;

    @Autowired
    private WXPayResource wxPayResource;

    @Autowired
    private AliPayResource aliPayResource;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private WxOrderService wxOrderService;

    /**
     * @Description: 微信扫码支付页面
     */
    @PostMapping(value = "/getWXPayQRCode")
    public PaymentInfoVO getWXPayQRCode(@RequestParam("merchantOrderId") String merchantOrderId,
                                     @RequestParam("merchantUserId") String merchantUserId) throws Exception {
        // 根据订单ID和用户ID查询订单详情
        PaymentOrder waitPayOrder = paymentOrderService.queryOrderByStatus(merchantUserId, merchantOrderId, PaymentStatus.WAIT_PAY.type);

        // 商品描述
        String body = "天天吃货-付款用户[" + merchantUserId + "]";
        // 商户订单号
        String out_trade_no = merchantOrderId;
        PaymentInfoVO paymentInfoVO = new PaymentInfoVO();

        // 从redis中去获得这笔订单的微信支付二维码，如果订单状态没有支付没有就放入，这样的做法防止用户频繁刷新而调用微信接口
        if (waitPayOrder != null) {
            String key = wxPayResource.getQrcodeKey() + ":" + merchantOrderId;
            String qrCodeUrl = redisUtils.get(key);

            if (StringUtils.isEmpty(qrCodeUrl)) {
                // 订单总金额，单位为分
                String total_fee = String.valueOf(waitPayOrder.getAmount());
//				String total_fee = "1";	// 测试用 1分钱

                // 统一下单
                PreOrderResult preOrderResult = wxOrderService.placeOrder(body, out_trade_no, total_fee);
                qrCodeUrl = preOrderResult.getCode_url();
                redisUtils.set(key, qrCodeUrl);
            }

            paymentInfoVO.setAmount(waitPayOrder.getAmount());
            paymentInfoVO.setMerchantOrderId(merchantOrderId);
            paymentInfoVO.setMerchantUserId(merchantUserId);
            paymentInfoVO.setQrCodeUrl(qrCodeUrl);

        }
        return paymentInfoVO;
    }
}
