package com.zgy.springsecurity_demo.service.impl;

import com.zgy.springsecurity_demo.pojo.LoginUser;
import com.zgy.springsecurity_demo.service.LoginService;
import com.zgy.springsecurity_demo.utils.JwtUtil;
import com.zgy.springsecurity_demo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.Objects;

/**
 * @author roxanne_waar
 * @date 2024/1/29 10:48
 * @description LoginServiceImpl
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    AuthenticationManager manager;

    @Autowired
    RedisCache redisCache;
    @Override
    public String login(String username, String password) {

        //调用AuthenticationManager的 authenticate方法,进行用户认证。
        Authentication authentication = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authenticate = null;
        authenticate = manager.authenticate(authentication);


        //获取经过验证的身份信息
        LoginUser principal = (LoginUser) authenticate.getPrincipal();

        //根据userid生成对应jwt
        String token = JwtUtil.createJWT(principal.getUserId().toString());

        //将用户信息存入redis，下一次请求可以识别
        redisCache.setCacheObject("userId:"+((LoginUser) authenticate.getPrincipal()).getUserId().toString(),principal);

        //返回token
        return token;



//        //将认证后的信息加入上下文
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }
}
