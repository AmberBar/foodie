package com.amber.foodie.pojo.bo;

import lombok.Data;

/**
 * 接收前台的购物车数据
 */
@Data
public class ShopcartBO {
    /**
     * 商品id
     */
    private String itemId;
    /**
     * 商品主图
     */
    private String itemImgUrl;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 规格id
     */
    private String specId;
    /**
     * 规格名称
     */
    private String specName;
    /**
     * 购买数量
     */
    private Integer buyCounts;
    /**
     * 折后价格
     */
    private String priceDiscount;
    /**
     * 正常价格
     */
    private String priceNormal;
}
