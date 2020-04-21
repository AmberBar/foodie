package com.amber.foodie.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 最新商品VO
 */
@Data
public class NewItemsVo {
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;
    private List<SimpleItemsVo> simpleItemList;
}
