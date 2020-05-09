package com.amber.foodie.common.wx.service;


import com.amber.foodie.common.wx.entity.PayResult;
import com.amber.foodie.common.wx.entity.PreOrderResult;

import java.io.InputStream;

/**
 * 处理微信支付的相关订单业务
 */
public interface WxOrderService {

	/**
	 * 调用微信接口进行统一下单
	 * @param body
	 * @param out_trade_no
	 * @param total_fee
	 * @return
	 * @throws Exception
	 */
	public PreOrderResult placeOrder(String body, String out_trade_no, String total_fee) throws Exception;

	/**
	 * 获取支付结果
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	public PayResult getWxPayResult(InputStream inStream) throws Exception;
	
}
