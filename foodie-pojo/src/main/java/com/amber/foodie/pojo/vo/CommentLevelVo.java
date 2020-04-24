package com.amber.foodie.pojo.vo;

import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class CommentLevelVo {

    private Integer totalCounts;
    private Integer goodCounts;
    private Integer badCounts;
    private Integer normalCounts;
}
