package com.amber.foodie.controller;

import com.amber.foodie.foodie.service.UsersService;
import com.amber.foodie.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping("/hello")
    public List<Users> getUsers() {
        return usersService.getUsers();
    }
}
