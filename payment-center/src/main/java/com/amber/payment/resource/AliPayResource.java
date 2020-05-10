package com.amber.payment.resource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="alipay")
@PropertySource("classpath:alipay.properties")
@Data
public class AliPayResource {

	private String appId;
	private String merchantPrivateKey;
	private String alipayPublicKey;

	private String notifyUrl;
	private String returnUrl;

	private String signType;
	private String charset;
	private String gatewayUrl;
}
