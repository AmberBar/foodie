package com.amber.foodie.mapper.customer;

import com.amber.foodie.pojo.vo.CommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsCustomerMapper {

    List<CommentVO> queryItemComments(@Param("paramMap") Map<String, Object> map);
}
