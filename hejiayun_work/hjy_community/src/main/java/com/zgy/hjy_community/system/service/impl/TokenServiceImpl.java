package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.common.constant.Constants;
import com.zgy.hjy_community.common.utils.RedisCache;
import com.zgy.hjy_community.common.utils.ServletUtils;
import com.zgy.hjy_community.common.utils.TokenUtils;
import com.zgy.hjy_community.system.domain.dto.LoginUserDto;
import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.mapper.SysUserMapper;
import com.zgy.hjy_community.system.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author roxanne_waar
 * @date 2024/2/7 17:10
 * @description TokenServiceIMpl
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    RedisCache redisCache;

    @Override
    public SysUser getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDto user = (LoginUserDto) authentication.getPrincipal();
        return userMapper.getUserByName(user.getUsername());
    }

    @Override
    public void clearUser() {
        String token = ServletUtils.getRequest().getHeader("Authorization");
        if(!StringUtils.hasText(token)){
            Claims claims = TokenUtils.parseToken(token);
            String uuid = (String) claims.get("uuid");
            redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + uuid);
        }
    }


}
