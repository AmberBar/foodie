package com.amber.foodie.mapper.customer;

import com.amber.foodie.pojo.vo.CategoryVO;
import com.amber.foodie.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface CategoryMapperCustomer {
    public List<CategoryVO> getSubCatList(Integer fatherId);

    List<NewItemsVO> getNewItems(@Param("paramMap") HashMap map);
}