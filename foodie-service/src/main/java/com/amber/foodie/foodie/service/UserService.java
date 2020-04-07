package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.User;
import com.amber.foodie.pojo.bo.UserBO;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User findByUsername(String username);

    int createUser(UserBO userBO);
}
