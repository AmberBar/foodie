package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.Category;
import com.amber.foodie.pojo.vo.CategoryVo;
import com.amber.foodie.pojo.vo.NewItemsVo;

import java.util.List;

public interface ICategoryService {
    /**
     * 查询所有一级分类
     *
     * @return
     */
    List<Category> queryRootLevelCat();

    /**
     * 根据一级分类id 查询子分类
     *
     * @return
     */
    List<CategoryVo> querySubCat(Integer fatherId);

    /**
     * 根据一级分类查询推荐商品
     *
     * @param fatherId
     * @return
     */
    List<NewItemsVo> getNewItems(Integer fatherId);
}
