package com.amber.foodie.controller;


import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.common.utils.PageResult;
import com.amber.foodie.foodie.service.ItemService;
import com.amber.foodie.pojo.Items;
import com.amber.foodie.pojo.ItemsImg;
import com.amber.foodie.pojo.ItemsParam;
import com.amber.foodie.pojo.ItemsSpec;
import com.amber.foodie.pojo.vo.CommentLevelVo;
import com.amber.foodie.pojo.vo.CommentVo;
import com.amber.foodie.pojo.vo.ItemInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        ItemInfoVo itemInfoVo = new ItemInfoVo();
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
        CommentLevelVo commentLevelVo = itemService.queryCommentCounts(itemId);
        return JsonResult.ok(commentLevelVo);
    }

    @ApiOperation(value = "查询商品评价", notes = "商品评价数量", httpMethod = "GET")
    @GetMapping("/comments")
    public JsonResult queryComments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name="level", value="等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name="page", value="当前页数", required = false)
            @RequestParam Integer page,
            @ApiParam(name="pageSize", value="每页大小", required = false)
            @RequestParam Integer pageSize
            ) {
        PageResult pageResult = itemService.queryComments(itemId, level, page, pageSize);
        return JsonResult.ok(pageResult);
    }
}
