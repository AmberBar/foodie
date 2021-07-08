package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.ItemsSpec;
import org.apache.ibatis.annotations.Param;

public interface ItemsSpecService {

    /**
     * 减少库存
     *
     * @param itemSpecId
     * @param buyCounts
     * @return
     */
    public int decreaseItemSpecStock(@Param("itemSpecId") String itemSpecId, @Param("buyCounts") Integer buyCounts);

    /**
     * 根据商品规格id查找商品规格
     *
     * @return
     */
    public ItemsSpec findById(String itemsSpecId);
}
