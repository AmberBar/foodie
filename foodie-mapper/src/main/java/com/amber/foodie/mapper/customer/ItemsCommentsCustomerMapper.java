package com.amber.foodie.mapper.customer;

import com.amber.foodie.pojo.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsCustomerMapper {

    List<CommentVo> queryItemComments(@Param("paramMap") Map<String, Object> map);
}
