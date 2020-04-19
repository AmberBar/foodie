package com.amber.foodie.controller;

import com.amber.foodie.common.utils.CookieUtils;
import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.common.utils.JsonUtils;
import com.amber.foodie.foodie.service.UserService;
import com.amber.foodie.pojo.User;
import com.amber.foodie.pojo.bo.UserBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api("用户登录注册管理")
@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    UserService userService;

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
        CookieUtils.setCookie(httpServletRequest, httpServletResponse, "user", JsonUtils.objectToJson(userNew), true);
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
