package com.amber.foodie.controller;

import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.common.utils.JsonUtil;
import com.amber.foodie.common.utils.RedisUtils;
import com.amber.foodie.foodie.service.ICarouselService;
import com.amber.foodie.foodie.service.ICategoryService;
import com.amber.foodie.pojo.Carousel;
import com.amber.foodie.pojo.Category;
import com.amber.foodie.pojo.enums.YesOrNo;
import com.amber.foodie.pojo.vo.CategoryVO;
import com.amber.foodie.pojo.vo.NewItemsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "首页接口", tags = {"首页接口"})
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    private ICarouselService carouselService;

    @Autowired
    private ICategoryService categoryService;

    @ApiOperation(value = "获取轮播图", notes = "获取轮播图", httpMethod = "GET")
    @GetMapping("/carousel")
    public JsonResult carousel() {
        String key = "carousel";
        String value = redisUtils.getCacheObject(key);
        List<Carousel> list = new ArrayList<>();
        if (value == null) {
            list = carouselService.quaryAll(YesOrNo.Yes.type);
            redisUtils.setCacheObject(key, JsonUtil.toJson(list));
        } else {
            list = JsonUtil.jsonToList(value, Carousel.class);
        }

        return JsonResult.ok(list);
    }

    /**
     * 首页分类展示需求
     * 1.第一次刷新主页查询大分类，渲染展示到首页
     * 2，如果鼠标上移到大分类，加载其子分类（懒加载）
     */
    @ApiOperation(value = "获取商品分类（一级分类）", notes = "获取商品分类（一级分类）", httpMethod = "GET")
    @GetMapping("/cats")
    public JsonResult cats() {
        String key = "cats";
        String value = redisUtils.getCacheObject(key);
        List<Category> list = new ArrayList<>();
        if (value == null) {
            list = categoryService.queryRootLevelCat();
            redisUtils.setCacheObject(key, JsonUtil.toJson(list));
        } else {
            list = JsonUtil.jsonToList(value, Category.class);
        }

        return JsonResult.ok(list);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JsonResult subCat(@ApiParam(name = "rootCatId", value = "一级分类id", required = true)
                             @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return JsonResult.errorMsg("分类不存在");
        }
        String key = "subCat:" + rootCatId;
        String value = redisUtils.getCacheObject(key);
        List<CategoryVO> list = new ArrayList<>();
        if (value == null) {
            list = categoryService.querySubCat(rootCatId);
            redisUtils.setCacheObject(key, JsonUtil.toJson(list));
        } else {
            list = JsonUtil.jsonToList(value, CategoryVO.class);
        }

        return JsonResult.ok(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JsonResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return JsonResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> list = categoryService.getNewItems(rootCatId);
        return JsonResult.ok(list);
    }

}
