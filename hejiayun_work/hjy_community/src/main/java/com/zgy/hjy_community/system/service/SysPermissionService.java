package com.zgy.hjy_community.system.service;

import com.zgy.hjy_community.system.domain.entity.SysUser;

import java.util.Set;

/**
 * @author roxanne_waar
 * @date 2024/2/7 16:45
 * @description SysPermissionServiceImpl
 */
public interface SysPermissionService {
    Set<String> getRolePermission(Long userId);
    Set<String> getMenuPermission(Long userId);
}
