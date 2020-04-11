package com.amber.foodie.foodie.service.impl;

import com.amber.foodie.common.enums.Sex;
import com.amber.foodie.foodie.service.UserService;
import com.amber.foodie.mapper.UserMapper;
import com.amber.foodie.pojo.User;
import com.amber.foodie.pojo.bo.UserBO;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int createUser(UserBO userBO) {
        User user = new User();
        user.setUsername(userBO.getUsername());
        user.setPassword(userBO.getPassword());
        user.setSex(Sex.SECRET.type);
        user.setNickname(userBO.getUsername());
        user.setFace("http://pic2.zhimg.com/50/v2-fb824dbb6578831f7b5d92accdae753a_hd.jpg");
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
//      // TODO 后续换成雪花算法id,先凑合着用
        user.setId(UUID.randomUUID().toString());
        // TODO加密
        user.setPassword(userBO.getPassword());
        return userMapper.insert(user);
    }
}
