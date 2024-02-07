package com.zgy.hjy_community.system.mapper;


import com.zgy.hjy_community.system.domain.entity.SysMenu;

import java.util.*;

/**
 * @author roxanne_waar
 * @date 2024/2/7 11:33
 * @description SysMenuMapper
 */
public interface SysMenuMapper {
    List<String> getPermissionsBYUserId(Long userId);

    List<SysMenu> getAllMenu();

    List<SysMenu> getMenuById(Long userId);

}
