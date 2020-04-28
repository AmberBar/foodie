package com.amber.foodie.pojo.vo;

import lombok.Data;

/**
 * 搜索商品列表Vo
 */
@Data
public class SearchItemsVo {
    private String itemId;
    private String itemName;
    private int sellCounts;
    private String imgUrl;
    private int price;
}

