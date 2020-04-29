package com.amber.foodie.foodie.service;

import com.amber.foodie.common.utils.PageResult;
import com.amber.foodie.pojo.Items;
import com.amber.foodie.pojo.ItemsImg;
import com.amber.foodie.pojo.ItemsParam;
import com.amber.foodie.pojo.ItemsSpec;
import com.amber.foodie.pojo.vo.CommentLevelVO;

import java.util.List;

/**
 * 商品信息
 */
public interface ItemService {

    /**
     * 根据商品Id查询详情
     *
     * @param
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品Id查询商品图片列表
     *
     * @param
     * @return
     */
    List<ItemsImg> queryItemsImgList(String itemId);

    /**
     * 根据商品Id查询商品规格
     *
     * @param
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品Id查询商品参数
     *
     * @param
     * @return
     */
    ItemsParam queyItemParam(String itemId);

    /**
     * 根据商品id查询评价数量
     *
     * @param itemId
     * @return
     */
    CommentLevelVO queryCommentCounts(String itemId);

    /**
     * 根据商品id和等级查询评价
     *
     * @param itemId
     * @param level
     * @return
     */
    PageResult queryComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 根据关键字搜索商品并分页排序
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PageResult searchItems(String keywords, String sort, Integer page, Integer pageSize);
}
