package com.amber.foodie.pojo.bo;

import lombok.Data;

@Data
public class SubmitOrderBO {
    private String userId; //用户id
    private String itemSpecIds; // 商品规格id
    private String addressId; // 地址id
    private Integer payMethod; // 支付方式
    private String leftMsg; // 留言
}
