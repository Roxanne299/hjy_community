package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.system.domain.dto.LoginUserDto;
import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.mapper.SysUserMapper;
import com.zgy.hjy_community.system.service.SysMenuService;
import com.zgy.hjy_community.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author roxanne_waar
 * @date 2024/2/5 21:06
 * @description UserDetailServiceImpl
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    SysUserMapper mapper;

    @Autowired
    SysPermissionServiceImpl permissionService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser loginUser = mapper.getUserByName(username);
        LoginUserDto loginUserDto = new LoginUserDto(loginUser);
        loginUserDto.setPerms(permissionService.getMenuPermission(loginUser.getUserId()).stream().collect(Collectors.toList()));
        loginUserDto.setRoles(permissionService.getRolePermission(loginUser.getUserId()).stream().collect(Collectors.toList()));

        return loginUserDto;
    }
}
