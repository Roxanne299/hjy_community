package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.system.domain.entity.SysUser;
import com.zgy.hjy_community.system.service.SysMenuService;
import com.zgy.hjy_community.system.service.SysPermissionService;
import com.zgy.hjy_community.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author roxanne_waar
 * @date 2024/2/7 16:47
 * @description SysPermissionServiceImpl
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    SysMenuService menuService;

    @Autowired
    SysRoleService roleService;
    @Override
    public Set<String> getRolePermission(Long userId) {
        List<String> roles = roleService.getRolesByUserId(userId);
        Set<String> new_roles = roles.stream().collect(Collectors.toSet());
        return new_roles;
    }

    @Override
    public Set<String> getMenuPermission(Long userId) {
        List<String> menus = menuService.getPermissionsByUserId(userId);
        if(SysUser.isAdmin(userId)){
            menus.add("*:*:*");
        }
        Set<String> new_menus = menus.stream().collect(Collectors.toSet());
        return new_menus;
    }
}
