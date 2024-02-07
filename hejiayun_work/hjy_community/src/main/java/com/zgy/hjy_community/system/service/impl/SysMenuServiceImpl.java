package com.zgy.hjy_community.system.service.impl;

import com.zgy.hjy_community.common.exception.AuthenticationExceptionImpl;
import com.zgy.hjy_community.system.domain.dto.SysMenuDto;
import com.zgy.hjy_community.system.domain.dto.SysMenuMeta;
import com.zgy.hjy_community.system.domain.entity.SysMenu;
import com.zgy.hjy_community.system.mapper.SysMenuMapper;
import com.zgy.hjy_community.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author roxanne_waar
 * @date 2024/2/7 11:43
 * @description SysMenuServiceImpl
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuMapper menuMapper;


    @Override
    public List<String> getPermissionsByUserId(Long userId) {
        return menuMapper.getPermissionsBYUserId(userId);
    }

    @Override
    public List<SysMenuDto> getAdminMenusAsTree() {
        List<SysMenu> allMenu = menuMapper.getAllMenu();
        List<SysMenuDto> tree = allMenu.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(menu -> {
                    SysMenuDto menuDto = transfer(menu);
                    menuDto.setChildren(getChildById(menu.getMenuId(),allMenu));
                    return menuDto;
                }).collect(Collectors.toList());
        return tree;
    }

    @Override
    public List<SysMenuDto> getMenusAsTreeById(Long userId) {
        List<SysMenu> allMenu = menuMapper.getMenuById(userId);
        List<SysMenuDto> tree = allMenu.stream()
                .filter(menu -> menu.getParentId() == 0)
                .map(menu -> {
                    SysMenuDto menuDto = transfer(menu);
                    menuDto.setChildren(getChildById(menu.getMenuId(),allMenu));
                    return menuDto;
                }).collect(Collectors.toList());
        return tree;
    }

    private SysMenuDto transfer(SysMenu sysMenu){
        SysMenuDto menuDto = new SysMenuDto();
        String processPath = sysMenu.getPath().replace("/","");
        menuDto.setName(processPath.substring(0,1).toUpperCase() + processPath.substring(1));
        menuDto.setPath(sysMenu.getPath());
        menuDto.setHidden(sysMenu.getVisible().equals("1"));
        menuDto.setRedirect("noRedirect");
        menuDto.setComponent(sysMenu.getComponent());
        menuDto.setAlwaysShow(true);
        menuDto.setMeta(new SysMenuMeta(sysMenu.getMenuName(),sysMenu.getIcon(),sysMenu.getIsCache() == 1));
        return menuDto;
    }
    
    private List<SysMenuDto> getChildById(Long parentId,List<SysMenu> list){
        List<SysMenuDto> subTree = list.stream()
                .filter(menu -> menu.getParentId() == parentId)
                .map(menu -> {
                    SysMenuDto menuDto = transfer(menu);
                    menuDto.setChildren(getChildById(menu.getMenuId(), list));
                    return menuDto;
                }).collect(Collectors.toList());
        return subTree;
    }
}
