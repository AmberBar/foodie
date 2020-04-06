package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.UserService;
import com.amber.foodie.mapper.UserMapper;
import com.amber.foodie.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Transactional
    @Override
    public List<User> getUsers() {
        return userMapper.selectAll();
    }

    @Override
    public User findByUsername(String username) {
        Example userExample = new Example(User.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        User user = userMapper.selectOneByExample(userExample);
        return user;
    }
}
