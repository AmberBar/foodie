package com.amber.foodie.pojo.enums;

/**
 * @Description: 订单状态 枚举
 */
public enum PaymentStatus {

	WAIT_PAY(10, "未付款"),
	PAID(20, "已支付"),
	WAIT_RECEIVE(30, "支付失败"),
	SUCCESS(40, "已退款");

	public final Integer type;
	public final String value;

	PaymentStatus(Integer type, String value){
		this.type = type;
		this.value = value;
	}

}
