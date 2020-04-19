package com.amber.foodie.foodie.service;

import com.amber.foodie.pojo.User;
import com.amber.foodie.pojo.bo.UserBO;

import java.util.List;

public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 注册用户名
     * @param userBO
     * @return
     */
    int createUser(UserBO userBO);

    /**
     * 通过用户名和密码查找用户
     * @param username
     * @param password
     * @return
     */
    User queryUserByUsernameAndPassword(String username, String password);
}
