package com.amber.foodie.mapper.customer;

import com.amber.foodie.my.mapper.MyMapper;
import com.amber.foodie.pojo.Category;
import com.amber.foodie.pojo.vo.CategoryVo;

import java.util.List;

public interface CategoryMapperCustomer {
    public List<CategoryVo> getSubCatList(Integer fatherId);
}