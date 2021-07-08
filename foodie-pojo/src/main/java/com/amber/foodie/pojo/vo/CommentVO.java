package com.amber.foodie.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 商品评价
 */
@Data
public class CommentVO {
    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createdTime;
    private String userFace;
    private String nickname;
}
