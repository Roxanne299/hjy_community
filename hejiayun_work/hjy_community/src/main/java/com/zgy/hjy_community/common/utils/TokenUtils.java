package com.zgy.hjy_community.common.utils;

import com.github.pagehelper.dialect.helper.HsqldbDialect;
import com.zgy.hjy_community.common.constant.Constants;
import com.zgy.hjy_community.system.domain.dto.LoginUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.HashMap;

/**
 * @author roxanne_waar
 * @date 2024/2/5 21:22
 * @description Token工具类
 */
public class TokenUtils {

    public  static final String secret = "zhanggy";

    public  static final Integer expiretime = 30;



    public  static String createToken(String subject){
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid",subject);

        return Jwts.builder().addClaims(map)
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }
    public  static Claims parseToken(String token){
        return (Claims) Jwts.parser()
                .setSigningKey(secret)
                .parse(token)
                .getBody();
    }

    public static LoginUserDto getUserDetails(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDto user = (LoginUserDto) authentication.getPrincipal();
        return user;
    }
    public static String getUsername(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDto user = (LoginUserDto) authentication.getPrincipal();
        return user.getUsername();
    }
}
