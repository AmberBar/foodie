package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.ItemsImg;

public interface ItemsImgService {
    /**
     * 通过商品id获取商品主图
     * @param itemId
     * @return
     */
    public ItemsImg findMainImgByItemId(String itemId);
}
