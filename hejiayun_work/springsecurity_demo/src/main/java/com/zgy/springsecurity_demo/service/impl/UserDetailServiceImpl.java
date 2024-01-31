package com.zgy.springsecurity_demo.service.impl;

import com.zgy.springsecurity_demo.pojo.LoginUser;
import com.zgy.springsecurity_demo.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author roxanne_waar
 * @date 2024/1/29 10:31
 * @description UserDetailServiceImpl
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    MyService service;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser userByName = service.findUserByName(username);
//        if(Objects.isNull(userByName)){
//            throw new RuntimeException("用户名或者密码错误");
//        }
        return userByName;
    }
}
