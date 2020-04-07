package com.amber.foodie.controller;

import com.amber.foodie.common.utils.JsonResult;
import com.amber.foodie.foodie.service.UserService;
import com.amber.foodie.pojo.User;
import com.amber.foodie.pojo.bo.UserBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/username/{username}")
    public JsonResult findUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return JsonResult.ok(user);
    }

    /**
     *
     * @return
     */
    @PostMapping("")
    public JsonResult createUser(@RequestBody @Validated UserBO userBO) {
        String password = userBO.getPassword();
        String username = userBO.getUsername();
        String confirmPassord = userBO.getConfirmPassword();
        // 1. 用户名是否存在
        User oldUser = userService.findByUsername(username);
        if (oldUser != null ) {
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
