package com.zgy.hjy_community.system.service;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/6 22:21
 * @description SysRoleService
 */
public interface SysRoleService {
    List<String> getRolesByUserId(Long userId);
}
