package com.amber.payment.resource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 从配置文件中读取微信配置文件
 */
@Component
@ConfigurationProperties(prefix="wxpay")
@PropertySource("classpath:wxpay.properties")
@Data
public class WXPayResource {
	private String qrcodeKey;
	private long qrcodeExpire;
	private String appId;
	private String merchantId;
	private String secrectKey;
	private String spbillCreateIp;
	private String notifyUrl;
	private String tradeType;
	private String placeOrderUrl;
}
