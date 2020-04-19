package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.Category;
import com.amber.foodie.pojo.vo.CategoryVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICategoryService {
    /**
     * 查询所有一级分类
     *
     * @return
     */
    List<Category> queryRootLevelCat();

    /**
     *根据一级分类id 查询子分类
     * @return
     */
    public List<CategoryVo> querySubCat(Integer fatherId);
//    /**
//     * 查询首页每一个一级分类下的6条最新商品数据
//     */
//    public List<NewItemsVo> getNewItems(Integer fatherId);
}
