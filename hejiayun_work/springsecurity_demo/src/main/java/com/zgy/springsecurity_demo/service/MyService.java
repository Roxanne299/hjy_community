package com.zgy.springsecurity_demo.service;

import com.zgy.springsecurity_demo.pojo.LoginUser;

/**
 * @author roxanne_waar
 * @date 2024/1/29 10:42
 * @description findUserByName
 */
public interface MyService {
    LoginUser findUserByName(String username);
}
