package com.zgy.springsecurity_demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zgy.springsecurity_demo.mapper.MyMapper;
import com.zgy.springsecurity_demo.pojo.LoginUser;
import com.zgy.springsecurity_demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author roxanne_waar
 * @date 2024/1/29 10:43
 * @description MyMapperImpl
 */
@Service
public class MyServiceImpl implements MyService {
    @Autowired
    MyMapper mapper;
    @Override
    public LoginUser findUserByName(String username) {
        QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        LoginUser loginUser = mapper.selectOne(wrapper);
        return loginUser;
    }
}
