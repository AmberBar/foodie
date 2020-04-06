package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.foodie.service.UsersService;
import com.amber.foodie.mapper.UsersMapper;
import com.amber.foodie.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    UsersMapper usersMapper;

    @Transactional
    @Override
    public List<Users> getUsers() {
        return usersMapper.selectAll();
    }
}
