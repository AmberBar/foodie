package com.amber.payment.service;


import com.amber.payment.enums.PayMethod;
import com.amber.payment.pojo.JsonResult;
import com.amber.payment.pojo.vo.MerchantOrdersVO;
import com.amber.payment.resource.AliPayResource;
import com.amber.payment.resource.WXPayResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private WXPayResource wxPayResource;

    @Autowired
    private AliPayResource aliPayResource;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private WxOrderService wxOrderService;

    /**
     * 接受商户订单信息，保存到自己的数据库
     */
    public JsonResult createMerchantOrder(MerchantOrdersVO merchantOrdersVO) throws Exception {
        String merchantOrderId = merchantOrdersVO.getMerchantOrderId(); // 订单id
        String merchantUserId = merchantOrdersVO.getMerchantUserId(); // 用户id
        Integer amount = merchantOrdersVO.getAmount(); // 实际支付订单金额
        Integer payMethod = merchantOrdersVO.getPayMethod(); // 支付方式
        String returnUrl = merchantOrdersVO.getReturnUrl(); // 回调链接

        if (StringUtils.isBlank(merchantOrderId)) {
            return JsonResult.errorMsg("参数[orderId]不能为空");
        }
        if (StringUtils.isBlank(merchantUserId)) {
            return JsonResult.errorMsg("参数[userId]不能为空");
        }
        if (amount == null || amount < 1) {
            return JsonResult.errorMsg("参数[realPayAmount]不能为空并且不能小于1");
        }
        if (payMethod == null) {
            return JsonResult.errorMsg("参数[payMethod]不能为空并且不能小于1");
        }
        if (payMethod != PayMethod.WEIXIN.type && payMethod != PayMethod.ALIPAY.type) {
            return JsonResult.errorMsg("参数[payMethod]目前只支持微信支付或支付宝支付");
        }
        if (StringUtils.isBlank(returnUrl)) {
            return JsonResult.errorMsg("参数[returnUrl]不能为空");
        }

        // 保存传来的商户订单信息
        boolean isSuccess = false;
        try {
            isSuccess = paymentOrderService.createPaymentOrder(merchantOrdersVO);
        } catch (Exception e) {
            e.printStackTrace();
            JsonResult.errorException(e.getMessage());
        }

        if (isSuccess) {
            return JsonResult.ok("商户订单创建成功！");
        } else {
            return JsonResult.errorMsg("商户订单创建失败，请重试...");
        }
    }

}
