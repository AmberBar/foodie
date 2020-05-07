DROP TABLE IF EXISTS `payment_order`;
CREATE TABLE `payment_order` (
  `id` varchar(64)   NOT NULL COMMENT '商品主键id',
  `merchant_order_id` varchar(64)   NOT NULL COMMENT '商户订单号',
  `merchant_user_id` varchar(64)  NOT NULL COMMENT '商户方的发起用户的用户主键id',
  `amount` int(11) NOT NULL COMMENT '实际支付总金额（包含商户所支付的订单费邮费总额）',
  `pay_method` int(11) NOT NULL COMMENT '累计销售 累计销售',
  `pay_status` int(11) NOT NULL COMMENT '支付状态 10：未支付 20：已支付 30：支付失败 40：已退款',
  `come_from` varchar(16) NOT NULL COMMENT '从哪一端来的，比如从天天吃货这门实战过来的',
  `return_url` varchar(64) NOT NULL COMMENT '支付成功后的通知地址，这个是开发者那一段的，不是第三方支付通知的地址',
  `is_delete` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除状态 1: 删除 0:未删除',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4   COMMENT='交易中心订单表';
