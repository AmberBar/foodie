package com.amber.foodie.controller;

import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.foodie.service.UserService;
import com.amber.foodie.pojo.User;
import com.amber.foodie.pojo.bo.UserBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("用户管理")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "通过用户名查找用户", notes = "通过用户名查找用户", httpMethod = "GET")
    @GetMapping("/username/{username}")
    public JsonResult findUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return JsonResult.ok(user);
    }

    /**
     * @return
     */
    @ApiOperation(value = "注册用户", notes = "注册用户", httpMethod = "POST")
    @PostMapping("")
    public JsonResult createUser(@RequestBody @Validated UserBO userBO) {
        String password = userBO.getPassword();
        String username = userBO.getUsername();
        String confirmPassord = userBO.getConfirmPassword();
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
}
