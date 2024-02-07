package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.system.domain.dto.LoginUserDto;
import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.mapper.SysUserMapper;
import com.zgy.hjy_community.system.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author roxanne_waar
 * @date 2024/2/7 17:10
 * @description TokenServiceIMpl
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    SysUserMapper userMapper;

    @Override
    public SysUser getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUserDto user = (LoginUserDto) authentication.getPrincipal();
        return userMapper.getUserByName(user.getUsername());
    }
}
