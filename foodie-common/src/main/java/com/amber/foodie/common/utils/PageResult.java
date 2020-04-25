package com.amber.foodie.common.utils;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * 用来返回分页Grid的数据格式
 */
@Data
public class PageResult {
    /**
     * 当前页数
     */
    private int page;
    /**
     * 总页数
     */
    private int total;
    /**
     * 总记录数
     */
    private long records;
    /**
     * 每行显示的内容
     */
    private List<?> rows;

    public static PageResult converPage(List<?> list, Integer page) {
        PageInfo pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        // 当前第几页
        pageResult.setPage(page);
        pageResult.setTotal(pageInfo.getPages());
        pageResult.setRecords(pageInfo.getTotal());
        pageResult.setRows(list);
        return pageResult;
    }
}
