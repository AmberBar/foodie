package com.amber.foodie.mapper.customer;

import com.amber.foodie.pojo.vo.CategoryVo;
import com.amber.foodie.pojo.vo.NewItemsVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CategoryMapperCustomer {
    public List<CategoryVo> getSubCatList(Integer fatherId);

    List<NewItemsVo> getNewItems(@Param("paramMap") HashMap map);
}