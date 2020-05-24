package com.amber.foodie.controller;

import com.amber.foodie.common.constant.Constant;
import com.amber.foodie.common.utils.CookieUtils;
import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.common.utils.JsonUtil;
import com.amber.foodie.common.utils.RedisUtils;
import com.amber.foodie.foodie.service.UserService;
import com.amber.foodie.pojo.User;
import com.amber.foodie.pojo.bo.ShopcartBO;
import com.amber.foodie.pojo.bo.UserBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Api("用户登录注册管理")
@RestController
@RequestMapping("/passport")
public class
PassportController {
    @Autowired
    UserService userService;

    @Autowired
    RedisUtils redisUtils;

    @ApiOperation(value = "判断用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JsonResult findUser(@RequestParam("username") String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return JsonResult.errorMsg("用户名已经存在");
        }
        return JsonResult.ok();
    }

    /**
     * @return
     */
    @ApiOperation(value = "注册用户", notes = "注册用户", httpMethod = "POST")
    @PostMapping("/regist")
    public JsonResult createUser(@RequestBody UserBO userBO) {
        String password = userBO.getPassword();
        String username = userBO.getUsername();
        String confirmPassord = userBO.getConfirmPassword();
        if (StringUtils.isBlank(password) || StringUtils.isBlank(username) || StringUtils.isBlank(confirmPassord)) {
            return JsonResult.errorMsg("用户名密码不能为空");
        }
        // 1. 用户名是否存在
        User oldUser = userService.findByUsername(username);
        if (oldUser != null) {
            return JsonResult.errorMsg("用户名已经存在");
        }
        // 2. 判断密码和确认密码是否相互匹配
        if (!password.equals(confirmPassord)) {
            return JsonResult.errorMsg("两次密码不正确");
        }
        userService.createUser(userBO);
        return JsonResult.ok();
    }


    /**
     * 1.
     *
     * @param userBO
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JsonResult login(@RequestBody UserBO userBO,
                            HttpServletRequest httpServletRequest,
                            HttpServletResponse httpServletResponse) {
        String password = userBO.getPassword();
        String username = userBO.getUsername();

        if (StringUtils.isBlank(password) || StringUtils.isBlank(username)) {
            return JsonResult.errorMsg("用户名密码不能为空");
        }

        User user = userService.queryUserByUsernameAndPassword(username, DigestUtils.md5Hex(password));
        if (user == null) {
            return JsonResult.errorMsg("用户名或者密码错误");
        }
        User userNew = setNUllProperty(user);
        CookieUtils.setCookie(httpServletRequest, httpServletResponse, "user",
                JsonUtil.toJson(userNew), true);

        //TODO 生成用户token 存入redis会话
        //TODO 同步购物车数据
        String cookieValue = CookieUtils.getCookieValue(httpServletRequest, Constant.FOOID_SHOPCART);

        String key = Constant.FOOID_SHOPCART + Constant.COLOL + user.getId();
        String redisValue = redisUtils.getCacheObject(key);

        /**
         * 1. cookie和redis都為空，不操作
         * 2. cookie為空，redis不为null, 把redis中的数据刷新到cookie
         */

        if (StringUtils.isBlank(redisValue)) {
            // redis为空, cookie为空
            if (StringUtils.isNotBlank(cookieValue)) {
                redisUtils.set(key, cookieValue);
            }
        } else {
            // redis不为空，cookie不为空, 合并cookie和redis中的数据, 如果specId一致以cookie为准
            if (StringUtils.isNotBlank(cookieValue)) {
                List<ShopcartBO> shopcartBOSFromCookie = JsonUtil.jsonToList(cookieValue, ShopcartBO.class);
                List<ShopcartBO> shopcartBOSFromRedis = JsonUtil.jsonToList(redisValue, ShopcartBO.class);
                shopcartBOSFromCookie.addAll(shopcartBOSFromRedis);
                List<ShopcartBO> list = new ArrayList<>();
                Map<String, List<ShopcartBO>> collect = shopcartBOSFromCookie.stream().collect(Collectors.groupingBy(ShopcartBO::getSpecId));
                Set<String> keys = collect.keySet();
                for (String s : keys) {
                    List<ShopcartBO> shopcartBOS = collect.get(key);
                    if (shopcartBOS.size() >= 1) {
                        ShopcartBO shopcartBO = shopcartBOS.get(1);
                        shopcartBO.setBuyCounts(shopcartBOS.stream().mapToInt(ShopcartBO::getBuyCounts).sum());
                        list.add(shopcartBO);
                    }
                }
                redisUtils.set(key, list);
            } else {
                // redis not null , cookie null 覆盖cookie
                CookieUtils.setCookie(httpServletRequest,
                        httpServletResponse,
                        Constant.FOOID_SHOPCART,
                        redisValue, true);
            }
        }
        return JsonResult.ok(userNew);
    }

    @ApiOperation(value = "用户注销", notes = "用户注销", httpMethod = "POST")
    @PostMapping("/logout")
    public JsonResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, "user");
        return JsonResult.ok();
    }

    private User setNUllProperty(User user) {
        user.setPassword(null);
        user.setMobile(null);
        return user;
    }
}
