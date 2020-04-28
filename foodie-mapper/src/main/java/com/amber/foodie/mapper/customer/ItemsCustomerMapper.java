package com.amber.foodie.mapper.customer;

import com.amber.foodie.my.mapper.MyMapper;
import com.amber.foodie.pojo.Items;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCustomerMapper extends MyMapper<Items> {
    List<Items> searchItems(@Param("paramsMap") Map<String, Object> paramsMap);
}