package com.amber.foodie.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 二级分类vo vo一般存放展示給前台的数据
 */
@Data
public class CategoryVO {
    private Integer id;
    private String name;
    private Integer type;
    private Integer fatherId;
    private List<SubCategoryVO> subCatList;
}
