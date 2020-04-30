package com.amber.foodie.pojo.bo;

import lombok.Data;

/**
 * 接收前台传输的收货地址 BO
 */
@Data
public class AddressBO {
    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;
}
