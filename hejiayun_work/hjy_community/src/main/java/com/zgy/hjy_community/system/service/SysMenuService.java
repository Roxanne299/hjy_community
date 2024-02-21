package com.zgy.hjy_community.system.service;

import com.zgy.hjy_community.system.domain.dto.SysMenuDto;
import com.zgy.hjy_community.system.domain.entity.SysMenu;

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


    List<SysMenu> getMenuList(SysMenu menu);

    SysMenu getMenuInfo(Long menuId);
    String checkMenuNameUnique(String menuName,Long ParentId);

    void addMenu(SysMenu sysMenu);

    void updateMenu(SysMenu menu);
    
    boolean hasChildren(Long menuId);

    void deleteMenuById(Long menuId);

    List<Long> selectMenuListByRoleId(Long roleId);

    List<SysMenu> getMenuTreeById(Long userId);
}
