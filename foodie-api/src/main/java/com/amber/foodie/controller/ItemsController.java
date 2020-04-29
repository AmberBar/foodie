package com.amber.foodie.controller;


import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.common.utils.PageResult;
import com.amber.foodie.foodie.service.ItemService;
import com.amber.foodie.pojo.Items;
import com.amber.foodie.pojo.ItemsImg;
import com.amber.foodie.pojo.ItemsParam;
import com.amber.foodie.pojo.ItemsSpec;
import com.amber.foodie.pojo.vo.CommentLevelVO;
import com.amber.foodie.pojo.vo.ItemInfoVO;
import com.amber.foodie.pojo.vo.ShopcartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口", tags = {"展示商品"})
@RestController
@RequestMapping("/items")
@Slf4j
public class ItemsController {

    @Autowired
    ItemService itemService;

    @ApiOperation(value = "商品详情", notes = "商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JsonResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {
        Items items = itemService.queryItemById(itemId);
        log.error("hahah");
        List<ItemsImg> itemsImgList = itemService.queryItemsImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queyItemParam(itemId);
        ItemInfoVO itemInfoVo = new ItemInfoVO();
        itemInfoVo.setItem(items);
        itemInfoVo.setItemImgList(itemsImgList);
        itemInfoVo.setItemSpecList(itemsSpecList);
        itemInfoVo.setItemParams(itemsParam);
        return JsonResult.ok(itemInfoVo);
    }

    @ApiOperation(value = "商品评价数量", notes = "商品评价数量", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JsonResult commentCounts(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId) {
        CommentLevelVO commentLevelVo = itemService.queryCommentCounts(itemId);
        return JsonResult.ok(commentLevelVo);
    }

    @ApiOperation(value = "查询商品评价", notes = "商品评价数量", httpMethod = "GET")
    @GetMapping("/comments")
    public JsonResult queryComments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "当前页数", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页大小", required = false)
            @RequestParam Integer pageSize
    ) {
        PageResult pageResult = itemService.queryComments(itemId, level, page, pageSize);
        return JsonResult.ok(pageResult);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JsonResult search(
            @ApiParam(name = "keywords", value = "关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序", required = true)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "当前页", required = false)
            @RequestParam(required = false) Integer page,
            @ApiParam(name = "pageSize", value = "每页条数", required = false)
            @RequestParam(required = false) Integer pageSize) {
        if (page == null) {
            page = PageResult.NORMOL_PAGE;
        }
        if (pageSize == null) {
            pageSize = PageResult.NORMOL_PAGE_SIZE;
        }
        PageResult pageResult = itemService.searchItems(keywords, sort, page, pageSize);
        return JsonResult.ok(pageResult);
    }


    /**
     *
     * @param itemSpecIds 商品规格字符串 "11001,11002"
     * @return
     */
    @ApiOperation(value = "刷新购物车", notes = "刷新购物车", httpMethod = "GET")
    @GetMapping("/refresh")
    public JsonResult refresh(
            @ApiParam(name = "itemSpecIds", value = "商品规格字符串 11001,11002", required = true)
            @RequestParam("itemSpecIds") String itemSpecIds) {
        List<ShopcartVO> shopcartVOS = itemService.queryItemsBySpecIds(itemSpecIds);
        return JsonResult.ok(shopcartVOS);
    }
}
