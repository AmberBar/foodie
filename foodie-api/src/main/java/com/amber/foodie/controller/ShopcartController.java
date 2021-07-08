package com.amber.foodie.controller;

import com.amber.foodie.common.constant.Constant;
import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.common.utils.JsonUtil;
import com.amber.foodie.common.utils.RedisUtils;
import com.amber.foodie.pojo.bo.ShopcartBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Api("购物车管理")
@RestController
@RequestMapping("/shopcart")
public class ShopcartController {

    @Autowired
    RedisUtils redisUtils;

    /**
     * 添加商品到购物车
     * 逻辑:
     * 1. 判断redis中是否存在改用户的购物车， 不存在则创建购物车存进redis
     * 2. 存在则判断购物车中是否包含当前新增商品，包含则增加数量，不包含则直接添加商品
     *
     * @param userId     用户id
     * @param shopcartBo 购物车参数
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JsonResult add(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam("userId") String userId,
            @ApiParam(name = "shopcartBo", value = "购物车", required = true)
            @RequestBody ShopcartBO shopcartBo,
            HttpServletRequest request,
            HttpServletResponse response) {
        // 同步cookie购物车的数据到redis
        if (StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("用户不存在");
        }

        String key = Constant.FOOID_SHOPCART + Constant.COLOL + userId;

        String value = redisUtils.getCacheObject(key);
        List<ShopcartBO> shopcartBOList = new ArrayList<>();
        if (StringUtils.isNotBlank(value)) {
            // redis有购物车
            // 判断购物车是否包含当前商品规格, 包含就增加count,不包含就新增
            boolean isContains = false;
            shopcartBOList = JsonUtil.jsonToList(value, ShopcartBO.class);
            for (ShopcartBO shopcart : shopcartBOList) {
                if (shopcart.getSpecId().equals(shopcartBo.getSpecId())) {
                    shopcart.setBuyCounts(shopcartBo.getBuyCounts() + shopcart.getBuyCounts());
                    isContains = true;
                }
            }
            if (!isContains) {
                shopcartBOList.add(shopcartBo);
            }
        } else {
            shopcartBOList = new ArrayList<>();
            // redis中没有购物车
            shopcartBOList.add(shopcartBo);
        }

        // 刷新redis中的购物车数据
        redisUtils.setCacheObject(key, JsonUtil.toJson(shopcartBOList));
        return JsonResult.ok();
    }

    /**
     * 前端用户在登陆的情况下，用户在页面删除购物车中商品，会同时在后端同步删除购物车
     * @param userId
     * @param itemSpecId
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "从购物车删除商品", notes = "从购物车删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public JsonResult del(
            @RequestParam String userId, @RequestParam String itemSpecId, HttpServletRequest request, HttpServletResponse response) {
        if(StringUtils.isBlank(userId)||StringUtils.isBlank(itemSpecId)){
            return JsonResult.errorMsg("参数不能为空");
        }

        String key = Constant.FOOID_SHOPCART + Constant.COLOL + userId;

        String value = redisUtils.getCacheObject(key);
        if (StringUtils.isBlank(value)) {
            return JsonResult.errorMsg("参数错误");
        }

        List<ShopcartBO> shopcartBOS = JsonUtil.jsonToList(value, ShopcartBO.class);
        for (ShopcartBO shopcartBO : shopcartBOS) {
            if (shopcartBO.getSpecId().equals(itemSpecId)) {
                shopcartBOS.remove(shopcartBO);
                break;
            }
        }
        redisUtils.set(key, JsonUtil.toJson(shopcartBOS));
        return JsonResult.ok();
    }

}
