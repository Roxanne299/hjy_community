package com.zgy.hjy_community.system.service;

import com.zgy.hjy_community.system.domain.dto.SysMenuDto;

import java.util.List;

/**
 * @author roxanne_waar
 * @date 2024/2/7 11:37
 * @description SysMenuService
 */
public interface SysMenuService {
    List<String> getPermissionsByUserId(Long userId);

    List<SysMenuDto> getAdminMenusAsTree();

    List<SysMenuDto> getMenusAsTreeById(Long userId);


}
