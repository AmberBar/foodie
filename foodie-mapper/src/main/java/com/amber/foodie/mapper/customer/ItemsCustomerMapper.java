package com.amber.foodie.mapper.customer;

import com.amber.foodie.my.mapper.MyMapper;
import com.amber.foodie.pojo.Items;
import com.amber.foodie.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCustomerMapper extends MyMapper<Items> {
    List<Items> searchItems(@Param("paramsMap") Map<String, Object> paramsMap);

    /**
     * 根据规格id查询商品信息
     * @param list
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(@Param("specIds") List<String> specIds);
}