package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.ICategoryService;
import com.amber.foodie.mapper.CategoryMapper;
import com.amber.foodie.mapper.customer.CategoryMapperCustomer;
import com.amber.foodie.pojo.Carousel;
import com.amber.foodie.pojo.Category;
import com.amber.foodie.pojo.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
@Service
@Transactional
public class CategoryServieImpl implements ICategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    CategoryMapperCustomer categoryMapperCustomerl;

    @Override
    public List<Category> queryRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        List<Category> carousels = categoryMapper.selectByExample(example);
        return carousels;
    }

    @Override
    public List<CategoryVo> querySubCat(Integer fatherId) {
        return categoryMapperCustomerl.getSubCatList(fatherId);
    }

//    @Override
//    public List<NewItemsVo> getNewItems(Integer fatherId) {
//        HashMap map=new HashMap();
//        map.put("rootCartId",fatherId);
//        return baseMapper.getNewItems(map);
//    }
}
